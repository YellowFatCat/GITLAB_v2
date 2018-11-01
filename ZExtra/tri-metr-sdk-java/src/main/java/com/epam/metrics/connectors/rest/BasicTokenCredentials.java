package com.epam.metrics.connectors.rest;

import com.epam.metrics.connectors.rest.jira.helpers.JiraException;
import com.epam.metrics.connectors.rest.jira.helpers.JiraRestClient;
import org.apache.http.HttpRequest;

public class BasicTokenCredentials implements ICredentials {
    private String apiAccessToken = null;

    /**
     * Creates new legacy HTTP credentials.
     *
     * @param token used for legacy api access (tri-metr)
     */
    public BasicTokenCredentials(String token) {
        this.apiAccessToken = token;
    }

    public String getApiAccessToken() {
        return apiAccessToken;
    }

    @Override
    public void initialize(JiraRestClient client) throws JiraException {

    }

    @Override
    public void authenticate(HttpRequest req) {
        //"Authorization", $"Bearer {apiAccessToken}");
        req.addHeader("Authorization", "Bearer " + this.apiAccessToken);
    }

    @Override
    public String getLogonName() {
        return "Token based authorization";
    }

    @Override
    public void logout(JiraRestClient client) throws JiraException {
        //
    }
}
