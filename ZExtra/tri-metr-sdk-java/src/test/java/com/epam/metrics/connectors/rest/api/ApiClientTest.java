package com.epam.metrics.connectors.rest.api;

import com.epam.metrics.IntegrationTest;
import com.epam.metrics.connectors.rest.BasicTokenCredentials;
import com.epam.metrics.connectors.rest.RestException;
import com.epam.metrics.connectors.rest.api.models.*;
import com.epam.metrics.connectors.rest.api.models.metricconfiguration.MetricConfigurations;
import com.epam.metrics.connectors.rest.api.models.periods.Period;
import com.epam.metrics.connectors.rest.api.models.periods.Periods;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertTrue;

//N.B. self-signed certificate should be added to trusted in java !
//Example 1: -Djavax.net.ssl.trustStore=C:\work\jira_related_stuff\InstallCert\jssecacerts
//Example 2: -Djavax.net.ssl.trustStore=jssecacerts
//(see jssecacerts attached)
@RunWith(SpringRunner.class)
@Category(IntegrationTest.class)
public class ApiClientTest {
    private final static String TEST_ENDPOINT = "https://sandbox.tri-metr.projects.epam.com/backend";

//    private final static String TEST_API_ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE1MDIxOTUwNjgsImV4cCI6MTUwNDgyMzA2OCwiaWF0IjoxNTAyMTk1MDY4LCJpc3MiOiJUUiBEZWxpdmVyeSBIZWFsdGggUG9ydGFsLiBTQU5EQk9YIiwianRpIjoiNjZjY2YyNDgtZDEwNC00ZGIxLThiYzEtNTFkODA1MjQ0NzZmIiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvbmFtZSI6IkRtaXRyaWlfWmlpYXRkaW5vdkBlcGFtLmNvbSIsInRva2VuX3R5cGUiOiJBY2Nlc3MifQ.ZnfjWhrP02cGOzGEF6L39Alu8KuddWdkmP_iOdNY3zY";
    // "Bearer "
    private final static String TEST_API_ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE1MDI0NTQxMzQsImV4cCI6MTUwNTA4MjEzNCwiaWF0IjoxNTAyNDU0MTM0LCJpc3MiOiJUUiBEZWxpdmVyeSBIZWFsdGggUG9ydGFsLiBTQU5EQk9YIiwianRpIjoiYjRmM2VjZGQtMDI0YS00NmY4LWI4N2UtNjJlYzIwNTM1ODZjIiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvbmFtZSI6IlNlcmdlaV9aaGVsZXpub3ZAZXBhbS5jb20iLCJ0b2tlbl90eXBlIjoiQWNjZXNzIiwidW5pdF9kZXZlbG9wZXIiOiI4MjgiLCJ1bml0X2FkbWluIjoiODI4In0.NGTUTYttoFvnDJXXrzJpbujvvZiyjeFauuBjZOHNCGg";
    private final static Integer TEST_UNIT_ID = 828;


    @Test
    public void testDeletePeriod() throws RestException, IOException, URISyntaxException {
        ApiClient apiClient = new ApiClient(TEST_ENDPOINT, new BasicTokenCredentials(TEST_API_ACCESS_TOKEN), TEST_UNIT_ID);
        Period period = new Period();
        period.setName("Sergey");
        period.setFromDate(new DateTime());
        period.setToDate(new DateTime().plusWeeks(2));
        //period.setPeriodId(7700);
        period.setExternalId(842);

        Period newPeriod = apiClient.submitPeriod(period);
        System.out.println(" submitted Period = " + newPeriod + ":");


        Object response = apiClient.deletePeriod(newPeriod.getPeriodId());
        //assertTrue(!response.isEmpty());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(" Period deleted response for unitId = " + TEST_UNIT_ID + ":");
        System.out.println(response);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }


    @Test
    public void testSubmitPeriod() throws IOException, URISyntaxException {
        ApiClient apiClient = new ApiClient(TEST_ENDPOINT, new BasicTokenCredentials(TEST_API_ACCESS_TOKEN), TEST_UNIT_ID);
        Period period = new Period();
        period.setName("Sergey");
        period.setFromDate(new DateTime());
        period.setToDate(new DateTime().plusWeeks(2));
        period.setPeriodId(7700);
        period.setExternalId(842);

        Period newPeriod = apiClient.submitPeriod(period);
        System.out.println(" submitted Period = " + newPeriod + ":");

//        assertTrue(!response.isEmpty());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(" Period submission response for unitId = " + TEST_UNIT_ID + ":");
        System.out.println("newPeriodId = " + newPeriod);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    @Test
    public void testGetPeriodByExternalId() throws RestException, IOException, URISyntaxException {
        ApiClient apiClient = new ApiClient(TEST_ENDPOINT, new BasicTokenCredentials(TEST_API_ACCESS_TOKEN), TEST_UNIT_ID);
        Period period = new Period();
        period.setName("Sergey");
        period.setFromDate(new DateTime());
        period.setToDate(new DateTime().plusWeeks(2));
        period.setPeriodId(7700);
        period.setExternalId(842);

        Period newPeriod = apiClient.submitPeriod(period);
        System.out.println(" submitted Period = " + newPeriod + ":");

        period = apiClient.getPeriodByExternalId("842");
//        assertTrue(!periods.isEmpty());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(" list of metrics for unitId = " + TEST_UNIT_ID + ":");
        System.out.println(period);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    @Test
    //@Ignore // returns 403 Forbidden - not enough rights
    public void testGetMetricConfigurations() throws RestException, IOException, URISyntaxException {
        ApiClient apiClient = new ApiClient(TEST_ENDPOINT, new BasicTokenCredentials(TEST_API_ACCESS_TOKEN), TEST_UNIT_ID);
        MetricConfigurations metrics = apiClient.getMetricConfigurations();
        assertTrue(!metrics.getMetricConfigurations().isEmpty());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(" list of metrics for unitId = " + TEST_UNIT_ID + ":");
        System.out.println(metrics);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    @Test
    public void testGetPeriods() throws RestException, IOException, URISyntaxException {
        ApiClient apiClient = new ApiClient(TEST_ENDPOINT, new BasicTokenCredentials(TEST_API_ACCESS_TOKEN), TEST_UNIT_ID);
        Periods periods = apiClient.getPeriods();
        assertTrue(!periods.getPeriods().isEmpty());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(" list of periods for unitId = " + TEST_UNIT_ID + ":");
        System.out.println(periods);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    @Test
    public void testVersionWithToken() throws RestException, IOException, URISyntaxException {
        ApiClient apiClient = new ApiClient(TEST_ENDPOINT, new BasicTokenCredentials(TEST_API_ACCESS_TOKEN), TEST_UNIT_ID);
        ApiVersion version = apiClient.getVersion();
        assertTrue(!version.getVersion().isEmpty());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("service version:");
        System.out.println(version);
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

}
