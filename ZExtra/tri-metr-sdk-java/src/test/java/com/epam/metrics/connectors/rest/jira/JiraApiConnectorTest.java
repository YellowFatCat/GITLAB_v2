package com.epam.metrics.connectors.rest.jira;

import com.epam.metrics.IntegrationTest;
import com.epam.metrics.connectors.rest.BasicCredentials;
import com.epam.metrics.connectors.rest.RestException;
import com.epam.metrics.connectors.rest.jira.helpers.JiraException;
import com.epam.metrics.connectors.rest.jira.models.issues.Issue;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

@RunWith(SpringRunner.class)
@Category(IntegrationTest.class)
public class JiraApiConnectorTest {

    private static final String TEST_JIRA_USERNAME = "Dmitrii.Ziiatdinov";
    private static final String TEST_JIRA_PASSWORD = "jirapotudanm85";
    private static final BasicCredentials TEST_JIRA_CREDENTIALS = new BasicCredentials(TEST_JIRA_USERNAME, TEST_JIRA_PASSWORD);
    private static final String TEST_JIRA_ENDPOINT = "http://jira.ald.int.westgroup.com";

    @Test
    public void testJiraApiConnector() throws JiraException, IOException, RestException, URISyntaxException {
        JiraApiConnector jiraClient = new com.epam.metrics.connectors.rest.jira.JiraApiConnector(TEST_JIRA_ENDPOINT, TEST_JIRA_CREDENTIALS);
        Collection<Issue> issues = jiraClient.getSearchResult("project = 'LEGAP' and assignee = Sergei.Zheleznov");
        System.out.println("jira connector (should be depricated)");
        if (issues.size() > 0) {
            System.out.println("have a result size = " + issues.size());
        } else {
            System.out.println("doesn't have a result");
        }
    }

}
