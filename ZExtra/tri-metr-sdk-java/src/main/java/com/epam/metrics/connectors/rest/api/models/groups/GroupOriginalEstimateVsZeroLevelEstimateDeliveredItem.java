package com.epam.metrics.connectors.rest.api.models.groups;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupOriginalEstimateVsZeroLevelEstimateDeliveredItem extends GroupItem {
    private Double originalEstimate;

    private Double zeroLevelEstimate;

    @Override
    public String getGroupItemType() {
        return "OriginalEstimateVsZeroLevelEstimate";
    }
}
