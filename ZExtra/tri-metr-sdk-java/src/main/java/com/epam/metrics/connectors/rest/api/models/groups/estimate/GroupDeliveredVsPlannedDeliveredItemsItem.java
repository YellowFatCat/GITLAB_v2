package com.epam.metrics.connectors.rest.api.models.groups.estimate;

public class GroupDeliveredVsPlannedDeliveredItemsItem extends EstimateGroupItem {
    @Override
    public String getGroupItemType() {
        return "DeliveredVsPlannedDelivered";
    }
}
