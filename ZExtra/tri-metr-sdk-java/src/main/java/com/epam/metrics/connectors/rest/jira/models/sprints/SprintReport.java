package com.epam.metrics.connectors.rest.jira.models.sprints;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergei_Zheleznov on 23.08.2017.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SprintReport {

    private Sprint sprint;

    @JsonProperty("contents")
    private SprintIssues sprintIssues;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class SprintIssues {

        private List<SprintReportIssue> completedIssues = new ArrayList<>();
        //private List<SprintReportIssue> issuesNotCompletedInCurrentSprint = new ArrayList<>();
        private List<SprintReportIssue> issuesCompletedInAnotherSprint = new ArrayList<>();
        //private List<SprintReportIssue> issuesRemovedFromSprint = new ArrayList<>();
        private List<SprintReportIssue> issuesAddedDuringSprint = new ArrayList<>();
        //private List<SprintReportIssue> allIssuesInSprint = new ArrayList<>();
    }
}
