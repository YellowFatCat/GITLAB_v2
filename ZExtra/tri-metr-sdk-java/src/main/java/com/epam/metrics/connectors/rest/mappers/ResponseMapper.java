package com.epam.metrics.connectors.rest.mappers;

import com.epam.metrics.connectors.rest.jira.models.issues.Issue;
import com.epam.metrics.connectors.rest.jira.models.issues.IssueResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;

import java.util.Map;

public class ResponseMapper {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static Issue convertToIssue(IssueResponse issueResponse) {

        String issueType = issueResponse.getFields().containsKey("issuetype") ? (String)((Map)(issueResponse.getFields()).get("issuetype")).get("name") : null;

        DateTime issueCreatedDate = null;
        if (issueResponse.getFields().containsKey("created") && issueResponse.getFields().get("created") != null) {
            issueCreatedDate = DateTime.parse((String)issueResponse.getFields().get("created"));
        }

        Issue issue = new Issue();
        issue.setId(issueResponse.getId());
        issue.setKey(issueResponse.getKey());
        issue.setIssueType(issueType);
        issue.setCreatedDate(issueCreatedDate);

        //TODO: changelog

        return issue;
    }
}
