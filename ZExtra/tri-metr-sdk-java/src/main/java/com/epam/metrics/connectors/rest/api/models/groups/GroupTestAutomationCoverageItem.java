package com.epam.metrics.connectors.rest.api.models.groups;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupTestAutomationCoverageItem extends GroupItem {
    enum Status {
        /// Indicates that it's not possible to determine automation status of item.
        Unknwon,
        /// Indicated that item is automated.
        Automated,
        /// Indicated that item is not automated.
        NotAutomated
    }

    private Status automationStatus = Status.Unknwon;

    @Override
    public String getGroupItemType() {
        return "TestAutomationCoverageItemsToAutomate";
    }
}
