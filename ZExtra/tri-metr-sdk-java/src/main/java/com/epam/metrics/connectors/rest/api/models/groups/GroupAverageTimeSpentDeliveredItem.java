package com.epam.metrics.connectors.rest.api.models.groups;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupAverageTimeSpentDeliveredItem extends GroupItem {
    private Double spent;

    @Override
    public String getGroupItemType() {
        return "AverageTimeSpentDelivered";
    }
}
