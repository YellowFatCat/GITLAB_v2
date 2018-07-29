package com.epam.metrics.connectors.rest.api.models.groups;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupPercentagePerPriorityReportedBug extends GroupItem {
    private Integer priority;

    @Override
    public String getGroupItemType() {
        return "PercentagePerPriorityReportedBugs";
    }
}
