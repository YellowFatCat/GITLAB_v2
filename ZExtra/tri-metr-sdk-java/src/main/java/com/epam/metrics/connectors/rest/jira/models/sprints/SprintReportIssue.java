package com.epam.metrics.connectors.rest.jira.models.sprints;

import com.epam.metrics.connectors.rest.jira.models.issues.Issue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SprintReportIssue extends Issue {
//    private Double originalEstimate;
//    private Double currentEstimate;

    @JsonProperty("status")
    public SprintIssueStatus status;

}
