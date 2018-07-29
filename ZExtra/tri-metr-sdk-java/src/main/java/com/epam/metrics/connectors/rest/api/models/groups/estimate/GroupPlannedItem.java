package com.epam.metrics.connectors.rest.api.models.groups.estimate;

public class GroupPlannedItem extends EstimateGroupItem {
    @Override
    public String getGroupItemType() {
        return "Planned";
    }
}
