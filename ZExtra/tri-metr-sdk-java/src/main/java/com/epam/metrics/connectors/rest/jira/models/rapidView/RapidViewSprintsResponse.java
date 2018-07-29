package com.epam.metrics.connectors.rest.jira.models.rapidView;

import com.epam.metrics.connectors.rest.jira.models.sprints.Sprint;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RapidViewSprintsResponse {

    private List<Sprint> sprints;
}
