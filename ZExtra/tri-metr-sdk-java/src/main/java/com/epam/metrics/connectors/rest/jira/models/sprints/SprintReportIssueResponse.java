package com.epam.metrics.connectors.rest.jira.models.sprints;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SprintReportIssueResponse {

    private int id;

    private String key;

    private EstimateStatistic originalEstimateStatistic;

    private EstimateStatistic currentEstimateStatistic;

    private String issueType;

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class EstimateStatistic {

        private int statFieldId;

        private EstimateStatisticFieldValue estimateFieldValue;

        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        private class EstimateStatisticFieldValue {

            private double estimateValue;
        }
    }
}
