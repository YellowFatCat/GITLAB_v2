package com.epam.metrics.connectors.rest.jira.models.searchoptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchOptions {
    public enum Fields { ALL, LIST, NONE }

    public Fields includeFields = Fields.ALL;

    public Collection<String> fieldList = new ArrayList<String>(0);

    public Boolean includeChangeLog = false;
}
