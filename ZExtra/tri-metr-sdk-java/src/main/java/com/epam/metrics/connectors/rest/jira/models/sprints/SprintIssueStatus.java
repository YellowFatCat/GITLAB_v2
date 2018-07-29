package com.epam.metrics.connectors.rest.jira.models.sprints;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Sergei_Zheleznov on 25.08.2017.
 */

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SprintIssueStatus {
    // TODO: make convertion from the enum of issue status

    private String name;
    private String description;

    /**
     * Allowed value types.
     */
    public enum ISSUE_STATUS {
        CLOSED("Closed"),
        OPEN("Open"),
        IN_PROGRESS("In Progress"),
        READY_TO_TEST("Ready to Test"),
        READY_FOR_CODE_REVIEW("Ready for Code Review"),
        DEV_COMPLETE("Dev Complete"),
        VERIFIED("Verified"),
        UNKNOWN("Unknown"); // if cannot match then use this

        private String name;

        ISSUE_STATUS(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    };

}
