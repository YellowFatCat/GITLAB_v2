package com.epam.metrics.connectors.rest.api.models.groups.estimate;

public class GroupDeliveredUnscheduledItem extends EstimateGroupItem {
    @Override
    public String getGroupItemType() {
        return "DeliveredUnscheduled";
    }
}
