package com.epam.metrics.connectors.rest.jira.models.searchoptions;

import java.util.Collection;

public class SearchOptionsBuilder {
    private SearchOptions searchOptions = new SearchOptions();

    public SearchOptionsBuilder includeFields(SearchOptions.Fields fields) {
        searchOptions.setIncludeFields(fields);
        return this;
    }

    public SearchOptionsBuilder fieldList(Collection<String> fieldList) {
        searchOptions.setFieldList(fieldList);
        return this;
    }

    public SearchOptionsBuilder includeChangeLog(boolean includeChangeLog) {
        searchOptions.setIncludeChangeLog(includeChangeLog);
        return this;
    }

    public SearchOptions build() {
        return searchOptions;
    }
}
