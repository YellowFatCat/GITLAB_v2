package com.epam.metrics.connectors.rest.jira.models.issues;


import com.epam.metrics.connectors.rest.jira.helpers.DateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {

    private int id;

    private String key;

    private String issueType;

    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime createdDate;

    private Map<String, Object> fields = new HashMap<>();

    private Map<String, Object> fieldsById = new HashMap<>();

    private List<IssueChange> changeLog = new ArrayList<>();

    private List<Issue> subIssues = new ArrayList<>();
}
