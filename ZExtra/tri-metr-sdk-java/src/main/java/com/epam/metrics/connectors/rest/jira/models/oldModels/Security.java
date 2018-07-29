package com.epam.metrics.connectors.rest.jira.models.oldModels;

import com.epam.metrics.connectors.rest.jira.helpers.JiraException;
import com.epam.metrics.connectors.rest.jira.helpers.JiraRestClient;
import com.epam.metrics.connectors.rest.jira.helpers.Field;
import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.net.URI;
import java.util.Map;

/**
 * Represents an issue security.
 */
public class Security extends Resource {

    private String description = null;
    private String name = null;

    /**
     * Creates a security from a JSON payload.
     *
     * @param restclient REST client instance
     * @param json JSON payload
     */
    public Security(JiraRestClient restclient, JSONObject json) {
        super(restclient);

        if (json != null)
            deserialise(json);
    }

    private void deserialise(JSONObject json) {
        Map map = json;

        self = Field.getString(map.get("self"));
        id = Field.getString(map.get("id"));
        description = Field.getString(map.get("description"));
        name = Field.getString(map.get("name"));
    }

    /**
     * Retrieves the given security record.
     *
     * @param restclient REST client instance
     * @param id Internal JIRA ID of the security
     *
     * @return a security instance
     *
     * @throws JiraException when the retrieval fails
     */
    public static Security get(JiraRestClient restclient, String id)
            throws JiraException {

        JSON result = null;

        try {
            result = restclient.get(new URI(getBaseUri() + "securitylevel/" + id));
        } catch (Exception ex) {
            throw new JiraException("Failed to retrieve security " + id, ex);
        }

        if (!(result instanceof JSONObject))
            throw new JiraException("JSON payload is malformed");

        return new Security(restclient, (JSONObject)result);
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

}
