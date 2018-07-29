package com.epam.metrics.connectors.rest.api.models.groups;

/**
 * Represents groups item.
 */
public abstract class GroupItem {

    protected String externalId;

    /*
     * Gets or sets external item id (JIRA issue key, TFS item id etc.). Helps to map items to items in reporting tool during debug process.
    */
    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    /*
     * Gets the type of the groups item.
     * Part of path for web api endpoint.
    */
    public abstract String getGroupItemType();
}