package com.epam.metrics.connectors.rest.api.models.groups;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupHydrationPercentNextIterationItem extends GroupItem {
    private Boolean isHydrated;

    private Integer nextIterationPosition;

    @Override
    public String getGroupItemType() {
        return "HydrationPercentNextIteration";
    }
}
