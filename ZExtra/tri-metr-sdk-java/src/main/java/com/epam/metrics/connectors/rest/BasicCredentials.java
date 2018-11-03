package com.epam.metrics.connectors.rest;

import com.epam.metrics.connectors.rest.jira.helpers.JiraException;
import com.epam.metrics.connectors.rest.jira.helpers.JiraRestClient;
import org.apache.http.HttpRequest;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.auth.BasicScheme;

public class BasicCredentials implements ICredentials {

    private String username;
    private String password;

    /**
     * Creates new legacy HTTP credentials.
     *
     * @param username
     * @param password
     */
    public BasicCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Sets the Authorization header for the given request.
     *
     * @param req HTTP request to authenticate
     */
    public void authenticate(HttpRequest req) {
        Credentials creds = new UsernamePasswordCredentials(username, password);
        req.addHeader(BasicScheme.authenticate(creds, "utf-8", false));
    }

    /**
     * Gets the logon name representing these credentials.
     *
     * @return logon name as a string
     */
    public String getLogonName() {
        return username;
    }

    public void initialize(JiraRestClient client) throws JiraException {
    }

    public void logout(JiraRestClient client) throws JiraException {
    }

}
