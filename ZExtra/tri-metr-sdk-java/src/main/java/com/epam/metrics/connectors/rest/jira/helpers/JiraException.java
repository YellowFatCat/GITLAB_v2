package com.epam.metrics.connectors.rest.jira.helpers;

/**
 * Derived from rcars jira-client
 */
public class JiraException extends Exception {

    public JiraException(String msg) {
        super(msg);
    }

    public JiraException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

