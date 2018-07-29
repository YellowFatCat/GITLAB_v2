package com.epam.metrics.connectors.rest.jira;

import com.epam.metrics.IntegrationTest;
import com.epam.metrics.connectors.rest.BasicCredentials;
import com.epam.metrics.connectors.rest.jira.helpers.JiraException;
import com.epam.metrics.connectors.rest.jira.models.oldModels.Issue;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@Ignore
@RunWith(SpringRunner.class)
@Category(IntegrationTest.class)
public class JiraClientTest {
    private static final String TEST_JIRA_USERNAME = "Dmitrii.Ziiatdinov";
    private static final String TEST_JIRA_PASSWORD = "xxxxxxxx";
    private static final BasicCredentials testJiraCredentials = new BasicCredentials(TEST_JIRA_USERNAME, TEST_JIRA_PASSWORD);
    private static final String TEST_JIRA_ENDPOINT = "http://jira.ald.int.westgroup.com";

    @Test
    public void testJiraClient() throws JiraException {
        JiraClient jiraClient = new JiraClient(TEST_JIRA_ENDPOINT, testJiraCredentials);
        Issue.SearchResult searchResult = jiraClient.searchIssues("project = 'LEGAP' and assignee = Sergei.Zheleznov");
        System.out.println("jira client");
        if (searchResult.total > 0) {
            System.out.println("have a result size = " + searchResult.total);
        } else {
            System.out.println("doesn't have a result");
        }

    }
}
