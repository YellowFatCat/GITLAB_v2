package com.epam.metrics.connectors.rest.api.models.groups;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupDefectContainmentReportedBug extends GroupItem {
    enum EnvironamentLevel {
        /*Indicates that item was found in unknown environment.*/
        Unknown,
        /*Indicates that item was found in upper environment.*/
        Upper,
        /*Indicates that item was found in lower environment.*/
        Lower
    };

    private EnvironamentLevel environement = EnvironamentLevel.Unknown;

    @Override
    public String getGroupItemType() {
        return "DefectContainmentReportedBugs";
    }
}
