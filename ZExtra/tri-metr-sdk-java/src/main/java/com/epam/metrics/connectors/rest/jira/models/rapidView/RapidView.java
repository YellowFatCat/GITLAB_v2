package com.epam.metrics.connectors.rest.jira.models.rapidView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RapidView {

    private int id;

    private String name;

    private boolean sprintSupportEnabled;
}
