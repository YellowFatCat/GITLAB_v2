package com.epam.metrics.connectors.rest;

import com.epam.metrics.connectors.rest.jira.helpers.JiraException;
import com.epam.metrics.connectors.rest.jira.helpers.JiraRestClient;
import org.apache.http.HttpRequest;

/**
 * Derived from rcars jira-client
 */
public interface ICredentials {

    void initialize(JiraRestClient client) throws JiraException;
    /**
     * Sets the Authorization header for the given request.
     *
     * @param req HTTP request to authenticate
     */
    void authenticate(HttpRequest req);

    /**
     * Gets the logon name representing these credentials.
     *
     * @return logon name as a string
     */
    String getLogonName();

    void logout(JiraRestClient client) throws JiraException;
}

