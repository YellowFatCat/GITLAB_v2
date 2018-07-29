package com.epam.metrics.connectors.rest.jira;

import com.epam.metrics.connectors.rest.ICredentials;
import com.epam.metrics.connectors.rest.RestException;
import com.epam.metrics.connectors.rest.jira.helpers.JiraException;
import com.epam.metrics.connectors.rest.jira.helpers.JiraRestClient;
import com.epam.metrics.connectors.rest.jira.models.*;
import com.epam.metrics.connectors.rest.jira.models.issues.Issue;
import com.epam.metrics.connectors.rest.jira.models.issues.IssueResponse;
import com.epam.metrics.connectors.rest.jira.models.searchoptions.SearchOptions;
import com.epam.metrics.connectors.rest.jira.models.searchoptions.SearchOptionsBuilder;
import com.epam.metrics.connectors.rest.jira.models.searchoptions.SearchResponse;
import com.epam.metrics.connectors.rest.mappers.ResponseMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static com.epam.metrics.connectors.rest.jira.helpers.HttpClientHelper.buildHttpClient;

public class JiraApiConnector {
    private final static Logger LOG = LoggerFactory.getLogger(JiraApiConnector.class);
    private ICredentials credentials;
    private String jiraBaseUrl;
    private JiraApiConnector apiConnector;

    private final JiraRestClient jiraRestClient;

    private final int NumberOfAttempts = 10;
    private final int ItemsCountPerRequest = 30;

    private final Map<String, List<String>> fieldNameToIdsMapping = new HashMap<>();
    private final Map<String, String> fieldIdToNameMapping = new HashMap<>();
    private final Map<Integer, String> prioritiesDictionary = new HashMap<>();
    private final ReentrantLock fieldsLock = new ReentrantLock();
    private final ReentrantLock prioritiesLock = new ReentrantLock();

    public JiraApiConnector(String jiraEndPoint, ICredentials credentials)
    {
        if (StringUtils.isEmpty(jiraEndPoint))
        {
            throw new IllegalArgumentException("Endpoint is null or empty");
        }
        if (credentials == null)
        {
            throw new IllegalArgumentException("Need to provide credentials");
        }

        this.credentials = credentials;
        this.jiraBaseUrl = jiraEndPoint;

        jiraRestClient = new JiraRestClient(buildHttpClient(), credentials, URI.create(jiraEndPoint));
    }

    /// <summary>
    /// Retrieves issues related to particular sprint.
    /// </summary>
    /// <param name="boardId">Id of the board, which contains sprint.</param>
    /// <param name="sprintId">Id of the sprint.</param>
    /// <param name="options">Issue search options.</param>
    /// <returns>List of issues related to sprint.</returns>
    public List<Issue> getSearchResult(String jqlQuery) throws JiraException, IOException, RestException, URISyntaxException {
        return getSearchResult(
                jqlQuery,
                new SearchOptionsBuilder().includeChangeLog(true).includeFields(SearchOptions.Fields.ALL).build()
        );
    }


    public List<Issue> getSearchResult(String jqlQuery, SearchOptions options) throws IOException, RestException, URISyntaxException {
        if (StringUtils.isEmpty(jqlQuery)) throw new IllegalArgumentException("jqlQuery search parameter is null or empty");
        Map<String, String> params = new HashMap<>();
        params.put("jql", jqlQuery);
        addQueryParamsFromSearchOptions(params, options);
        ResponseEntity<SearchResponse> response = jiraRestClient.get("/rest/api/2/search", params, SearchResponse.class);
        return response.getBody().getIssues().stream().map(s -> ResponseMapper.convertToIssue(s)).collect(Collectors.toList());
    }

    private void addQueryParamsFromSearchOptions(Map<String, String> params, SearchOptions options) {
        String queryString;
        List<String> defaultFields = Arrays.asList("issuetype", "created");

        switch (options.getIncludeFields()) {
            case ALL:
                break;
            case NONE:
                params.put("fields", String.join(",", defaultFields));
                break;
            case LIST:
                if (options.getFieldList() == null || options.getFieldList().size() == 0) {
                    throw new IllegalArgumentException("Expected options.fieldList to be not empty");
                }

                //TODO: get field ids from field names
                List<String> fieldIds = new ArrayList<>();
                fieldIds.addAll(defaultFields);
                params.put("fields", String.join(",", fieldIds));
                break;
            default:
                throw new IllegalArgumentException("Unexpected option " + options.getIncludeFields());
        }

        if (options.getIncludeChangeLog()) {
            params.put("expand", "changelog");
        }
    }

    /// <summary>
    /// Translates JIRA field name to list of JIRA field ids.
    /// </summary>
    /// <param name="fieldName">JIRA field name.</param>
    /// <returns>List of JIRA field ids.</returns>
    public List<String> getFieldIdsByName(String fieldName) throws IOException, URISyntaxException {
        if (StringUtils.isEmpty(fieldName)) {
            throw new IllegalArgumentException("Field name is null or empty");
        }

        ensureFieldsMetaLoaded();
        if (fieldNameToIdsMapping.containsKey(fieldName)) {
            return fieldNameToIdsMapping.get(fieldName);
        }
        return new ArrayList<>();
    }

    public String getFieldNameById(String fieldId) throws IOException, URISyntaxException {
        if (StringUtils.isEmpty(fieldId)) {
            throw new IllegalArgumentException("Field ID is null or empty");
        }
        ensureFieldsMetaLoaded();
        if(fieldIdToNameMapping.containsKey(fieldId)) {
            return fieldIdToNameMapping.get(fieldId);
        }
        return null;
    }

    public Integer getPriorityValue(String priorityName) throws IOException, URISyntaxException {
        ensurePrioritiesLoaded();
        if (prioritiesDictionary.containsValue(priorityName)) {
            return prioritiesDictionary.keySet().stream().filter(k -> priorityName.equals(prioritiesDictionary.get(k))).findAny().get();
        }
        return null;
    }

    //TODO
    private void makeChangeLogRequest(List<Issue> issuesWithoutChangelog) {
        issuesWithoutChangelog.parallelStream().forEach(issueWithoutChangelog -> {
            IssueResponse issueResponse = new IssueResponse();
            try {
                ResponseEntity<IssueResponse> response =  jiraRestClient.get("/rest/api/2/issue/{issueWithoutChangelog.Key}?fields=none&expand=changelog", IssueResponse.class);
                issueResponse = response.getBody();
            } catch (IOException | URISyntaxException e) {
                LOG.error(e.getMessage());
            }
            //issueWithoutChangelog.getChangeLog().addAll(ResponseMapper.);
        });
    }

    private void ensurePrioritiesLoaded() throws IOException, URISyntaxException {
        List<PriorityResponse> priorities;
        if (prioritiesDictionary.isEmpty()) {
            prioritiesLock.lock();
            try {
                if (prioritiesDictionary.isEmpty()) {
                    ResponseEntity<PrioritiesResponse> response = jiraRestClient.get("/rest/api/2/priority", PrioritiesResponse.class);
                    priorities = response.getBody().getPriorities();
                    if (priorities.isEmpty()) {
                        LOG.error("Unexpected priorities response from JIRA, zero items recieved");
                    } else {
                        priorities.forEach(priority -> {
                            prioritiesDictionary.put(priority.getId(), priority.getName());
                        });
                    }
                }
            } finally {
                prioritiesLock.unlock();
            }
        }
    }

    private void ensureFieldsMetaLoaded() throws IOException, URISyntaxException {
        List<FieldResponse> fields;
        if (fieldIdToNameMapping.isEmpty()) {
            fieldsLock.lock();
            try {
                if (fieldIdToNameMapping.isEmpty()) {
                    ResponseEntity<FieldsResponse> response = jiraRestClient.get("/rest/api/2/field", FieldsResponse.class);
                    fields = response.getBody().getFields();
                    if (fields.isEmpty()) {
                        LOG.error("Unexpected fields response from JIRA, zero items recieved");
                    } else {
                        fields.forEach(field -> {
                            fieldIdToNameMapping.put(field.getId(), field.getName());

                            if(fieldNameToIdsMapping.containsKey(field.getName())) {
                                fieldNameToIdsMapping.get(field.getName()).add(field.getId());
                            } else {
                                fieldNameToIdsMapping.put(field.getName(), Arrays.asList(field.getId()));
                            }
                        });
                    }
                }
            } finally {
                fieldsLock.unlock();
            }
        }
    }
}