package com.epam.metrics.connectors.rest.api.models.groups.estimate;

public class GroupDeliveredScheduledItem extends EstimateGroupItem {
    @Override
    public String getGroupItemType() {
        return "DeliveredScheduled";
    }
}
