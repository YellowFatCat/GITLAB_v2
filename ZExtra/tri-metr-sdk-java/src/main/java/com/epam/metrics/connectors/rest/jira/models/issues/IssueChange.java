package com.epam.metrics.connectors.rest.jira.models.issues;

import com.epam.metrics.connectors.rest.jira.helpers.DateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueChange {

    @JsonDeserialize(using = DateTimeDeserializer.class)
    private DateTime changeDate;

    private List<ChangeField> changeFields = new ArrayList<>();

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class ChangeField {
        private String name;
        private String previousValue;
        private String currentValue;
    }

    /**
     * aux method used for object creation
     */
    public IssueChange addChangeField(String name, String previousValue, String currentValue) {
        ChangeField changeField = new ChangeField();
        changeField.setName(name);
        changeField.setPreviousValue(previousValue);
        changeField.setCurrentValue(currentValue);
        this.getChangeFields().add(changeField);
        return this;
    }
}
