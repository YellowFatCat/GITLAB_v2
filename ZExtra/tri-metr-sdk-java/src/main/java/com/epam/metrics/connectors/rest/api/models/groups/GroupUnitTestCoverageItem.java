package com.epam.metrics.connectors.rest.api.models.groups;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupUnitTestCoverageItem extends GroupItem {
    private String module;

    private Double coverage;

    @Override
    public String getGroupItemType() {
        return "UnitTestCoverage";
    }
}
