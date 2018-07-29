package com.epam.metrics.connectors.rest.jira.models;

import com.epam.metrics.connectors.rest.jira.helpers.DateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties (ignoreUnknown = true)
public class History {

    private List<HistoryItem> historyItems;

    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime created;

    @Getter
    @Setter
    @JsonIgnoreProperties (ignoreUnknown = true)
    public class HistoryItem {
        private String field;
        private String previousValue;
        private String newValue;
        private String fieldType;
    }
}
