package com.epam.metrics.connectors.rest.api.models.groups;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

@Getter
@Setter
public class GroupPassRatioItem extends GroupItem {
    private DateTime date;

    private Double passRatioValue;

    private String testSuite;

    private String environment;

    @Override
    public String getGroupItemType() {
        return "PassRatio";
    }
}
