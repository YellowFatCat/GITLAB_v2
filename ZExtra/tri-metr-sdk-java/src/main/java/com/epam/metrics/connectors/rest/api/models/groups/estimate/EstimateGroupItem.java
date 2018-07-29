package com.epam.metrics.connectors.rest.api.models.groups.estimate;

import com.epam.metrics.connectors.rest.api.models.groups.GroupItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class EstimateGroupItem extends GroupItem {
    private Double estimate;
}
