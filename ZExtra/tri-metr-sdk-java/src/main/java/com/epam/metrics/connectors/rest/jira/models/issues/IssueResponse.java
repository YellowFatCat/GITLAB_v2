package com.epam.metrics.connectors.rest.jira.models.issues;

import com.epam.metrics.connectors.rest.jira.models.ChangeLogResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueResponse {

    private int id;

    private String key;

    private Map<String, Object> fields;

    private ChangeLogResponse changelog;
}
