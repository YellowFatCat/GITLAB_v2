package com.epam.metrics.connectors.rest.jira.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldResponse {

    private String  id;

    private String name;
}
