package com.epam.metrics.example;

import com.epam.metrics.connectors.rest.BasicCredentials;
import com.epam.metrics.connectors.rest.BasicTokenCredentials;
import com.epam.metrics.connectors.rest.RestException;
import com.epam.metrics.connectors.rest.api.ApiClient;
import com.epam.metrics.connectors.rest.api.models.StreamDetails;
import com.epam.metrics.connectors.rest.api.models.groups.GroupItem;
import com.epam.metrics.connectors.rest.api.models.groups.estimate.GroupVelocityDeliveredItemsItem;
import com.epam.metrics.connectors.rest.api.models.metricconfiguration.MetricConfiguration;
import com.epam.metrics.connectors.rest.api.models.metricconfiguration.MetricType;
import com.epam.metrics.connectors.rest.api.models.periods.Period;
import com.epam.metrics.connectors.rest.api.models.periods.Periods;
import com.epam.metrics.connectors.rest.jira.models.searchoptions.SearchOptions;
import com.epam.metrics.connectors.rest.jira.models.issues.Issue;
import com.epam.metrics.connectors.rest.jira.models.sprints.*;
import com.epam.metrics.example.config.ApplicationProperties;
import com.epam.metrics.connectors.rest.jira.JiraClient;
import com.epam.metrics.connectors.rest.jira.helpers.JiraException;


import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

//N.B. self-signed certificate should be added to trusted in java !
//Example 1: -Djavax.net.ssl.trustStore=C:\work\jira_related_stuff\InstallCert\jssecacerts
//Example 2: -Djavax.net.ssl.trustStore=jssecacerts
//(see jssecacerts attached)

@SpringBootApplication
@EnableConfigurationProperties({ApplicationProperties.class})
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private ApplicationContext context;

    private final ApplicationProperties applicationProperties;

    private final BasicCredentials jiraCredentials;
    private final JiraClient jiraClient;
    private final ApiClient apiClient;

    private final int rapidViewId;

    public Application(ApplicationProperties applicationProperties) throws JiraException {
        this.applicationProperties = applicationProperties;

        log.info("JIRA host = " + applicationProperties.getJiraEndPoint());

        jiraCredentials = new BasicCredentials(applicationProperties.getUsername(), applicationProperties.getPassword());
        jiraClient = new JiraClient(applicationProperties.getJiraEndPoint(), jiraCredentials);

        log.info("METRICS API host = " + applicationProperties.getApiEndPoint());

        apiClient = new ApiClient(applicationProperties.getApiEndPoint(), new BasicTokenCredentials(applicationProperties.getApiAccessToken()), applicationProperties.getUnitId());

        rapidViewId = applicationProperties.getRapidViewId();
        log.info("Rapid View = " + applicationProperties.getApiEndPoint());
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private final Map<Integer, SprintReport> sprintReports = new HashMap<>();

    private SprintReport getSprintReport(int rapidViewId, int sprintId) throws IOException, URISyntaxException {
        if (sprintReports.containsKey(sprintId)) return sprintReports.get(sprintId);

        SearchOptions options = new SearchOptions();
        options.fieldList = Arrays.asList("Resolution", "SprintIssueStatus", "Issue Type", "Sprint", "Story Points");
        options.includeFields = SearchOptions.Fields.LIST;
        options.includeChangeLog = true;

        SprintReport sprintReport = jiraClient.getSprintReport(rapidViewId, sprintId, options);
        sprintReports.put(sprintId, sprintReport);
        return sprintReport;
    }

    private List<Issue> getIssuesInSprintAsOfDate(int rapidViewId, Sprint sprint, DateTime date) {
        List<String> allowedIssueTypes = Arrays.asList("Bug", "Story");
        List<String> excludedResolutions = Arrays.asList("Obsolete", "Duplicate", "Cannot Reproduce", "Canceled");
//        IEnumerable<SprintReportIssue> sprintIssuesByReport = GetSprintReport(rapidViewId, sprint.Id)
//                .AllIssuesInSprint
//                .Where(x => allowedIssueTypes.Contains(x.IssueType))
//                .Where(x => x.CreatedDate <= date);
//
        List<Issue> sprintIssues = new ArrayList<>();
//        foreach (SprintReportIssue issue in sprintIssuesByReport)
//        {
//            bool issueIsInSprint = false;
//
//            // sprint field value has different formats in issue fields collection and changelog (please inspect in debug)
//            // also this field may contain several sprints and in this case field format also different
//            // the purpose of the code below to convert all cases to array of strings and process that array
//            string sprintValue;
//            if (ChangeLogHelper.TryGetFieldValueFromChangeLogAsOfDate(issue, "Sprint", date, out sprintValue))
//            {
//                issueIsInSprint = FieldsHelper.ParseSprintFieldValueFromChangeLog(sprintValue).Contains(sprint.Name);
//            }
//            else
//            {
//                issueIsInSprint = FieldsHelper.ParseSprintFieldValue(issue.Fields["Sprint"]).Contains(sprint.Name);
//            }
//
//            // always inspect JIRA responses for field - it could vary depending on jira version you use
//            string resolution = ChangeLogHelper.ChangeLogHelper(issue, "Resolution", date, x => (string)x?["name"]);
//            bool hasValidResolution = excludedResolutions.Contains(resolution) == false;
//
//            if (issueIsInSprint && hasValidResolution)
//            {
//                sprintIssues.Add(issue);
//            }
//        }
//
        return sprintIssues;
    }

    private List<SprintReportIssue> getDeliveredIssuesInSprint(SprintReport sprintReport) {
        List<String> deliveredStatuses = Arrays.asList(
                SprintIssueStatus.ISSUE_STATUS.VERIFIED.toString(),
                SprintIssueStatus.ISSUE_STATUS.CLOSED.toString()
        );

//        List<Issue> sprintIssues = getIssuesInSprintAsOfDate(rapidViewId, sprint, sprint.getEndDate());

        List<SprintReportIssue> deliveredIssues = sprintReport.getSprintIssues().getCompletedIssues().stream()
                .filter(i -> deliveredStatuses.contains(i.getStatus().getName())).collect(Collectors.toList());


        //fixme:
//        foreach (Issue issue in sprintIssues)
//        {
//            string statusValue = ChangeLogHelper.GetFieldValueAsOfDate(issue, "SprintIssueStatus", sprint.EndDate.Value, x => (string)x["name"]);
//
//            if (deliveredStatuses.Contains(statusValue))
//            {
//                deliveredIssues.Add(issue);
//            }
//        }

        return deliveredIssues;
    }

    @PostConstruct
    public void initApplication() throws JiraException, IOException, URISyntaxException, RestException, InterruptedException {


        log.info("Submit stream details");
        apiClient.submitStreamDetails(getStreamDetails());
        log.info("Ok");

        log.info("Submit active metrics configuration");
        apiClient.submitMetricsConfiguration(getActiveMetrics());
        log.info("Ok");

        log.info("Get available sprints from JIRA");

        // Try to extract data for the last year.
        DateTime effectiveFrom = DateTime.now().minusYears(1);


//         get all closed sprints from JIRA
//         sprint name, start, end etc. will be available
//         rapid view id you could get from url to your board
//         it should look similar to https://jiraeu.epam.com/secure/RapidBoard.jspa?rapidView=47567

//        int rapidViewId = 47567;
//        IEnumerable<Sprint> sprints = apiClient.getClosedSprints(rapidViewId)
//                .OrderBy(x => x.StartDate)
//                .Where(x => x.StartDate > effectiveFrom);
//

        // get Closed Sprints
        final Sprints sprintsWithDates = new Sprints();

        final List<SprintReport> sprintReports = jiraClient.getSprintReports(rapidViewId);

        sprintReports.forEach(r -> sprintsWithDates.getSprints().add(r.getSprint()));
        log.info("Found Jira Sprints : " + sprintsWithDates.getSprints().size());


        //TODO: Check that it complains with this version:
        // https://git.epam.com/tri-metr/stream-jira-example/blob/master/jira-example/jira-example/Program.cs
        Periods submittedPeriods = apiClient.getPeriods();

        // In Web API we operate periods as more general term than sprint.
        // Period also can be a release or some "window" for Kanban process.
        // Loading already submitted periods is required as we should not submit same period twice
        for (SprintReport sprintReport : sprintReports) {
            Sprint sprint = sprintReport.getSprint();
            log.info("Process sprint: " + sprint.getName());

            // in order to submit group items we should register new period in API and get Id of submitted or existing period
            Period period = SubmitPeriodIfRequired(submittedPeriods, sprint);


            if (!apiClient.areGroupItemsSubmitted(period.getPeriodId(), GroupVelocityDeliveredItemsItem.class)) {
                List<SprintReportIssue> deliveredIssues = getDeliveredIssuesInSprint(sprintReport);
                //todo:
                // More about grops you can find here [link].
                submitVelocityGroupsForPeriod(period, deliveredIssues);
            }


            //// #########################################################################################################
            //// ############################## Code bellow migrated from .NET is overcomplicated ########################
            //// ######### Need to redesign it with help of the query of getSprintReport() and caching            ########
            //// #########################################################################################################

            if (!apiClient.areGroupItemsSubmitted(period.getPeriodId(), GroupVelocityDeliveredItemsItem.class)) {
                log.info("Group Items are Submitted for period: " + period.getPeriodId());
                List<SprintReportIssue> deliveredIssues = getDeliveredIssuesInSprint(sprintReport);
                log.info("Delivered Issues size: " + deliveredIssues.size());
                //todo:
                // More about grops you can find here [link].
                //SubmitVelocityGroupsForPeriod(period, deliveredIssues);

            }


            // group items are intended to be submitted only once
            // be careful and submit correct data
            // same for all groups below
//            if (_apiConnector.AreGroupItemsSubmitted<GroupVelocityDeliveredItem>(period.PeriodId) == false)
//            {
//                // make query to JIRA and collect delivered issues for sprint
//                List<Issue> deliveredIssues = GetDeliveredIssuesInSprint(sprint, rapidViewId);
//
//                // Submit group of issues for velocity metric
//                // More about grops you can find here [link].
//                SubmitVelocityGroupsForPeriod(period, deliveredIssues);
//            }


        }


        // if you have JIRA 7+ version you may use JiraAgileConnector instead
//        JiraAgileConnector agileConnector = new JiraAgileConnector();

        // you could retrieve board id(same as rapid view id, but in terms of JIRA agile api) via project key and board name
//        int boardId = jiraClient.getBoard("TRIEVN", "TRI-EVN Agile Board").Id;

        // closed sprints are available from Agile api using board id (rapid view id works also as seems to be the same for our JIRA setup)
        // configuring (or hardcoding) board id could be more reliable - board name could be changed in future
        // this method works much faster then greenhopper one in case you have large number of sprints
//        IEnumerable<Sprint> sprintsFromAgileApi = agileConnector.GetClosedSprints(boardId);


        //#############################//#############################
        //#### Added the legacy JIRA/METRICS functionality bellow s####
        //#############################//#############################

        // TODO: Code bellow should be cleaned after implementation of console

        // Jira Configuration
//        Issue.SearchResult searchResult = jiraClient.searchIssues("project = 'LEGAP' and assignee = Sergei.Zheleznov");
//        log.info("jira client");
//        if (searchResult.total > 0) {
//            log.info("have a result size = " + searchResult.total);
//        } else {
//            log.info("doesn't have a result");
//        }


        // API Configuration

        Period period = new Period();
        period.setName("Sergey");
        period.setFromDate(new DateTime());
        period.setToDate(new DateTime().plusWeeks(2));
        //period.setPeriodId(7700);
        period.setExternalId(842);

        Period newPeriod = apiClient.submitPeriod(period);
        log.info(" submitted Period = " + newPeriod + ":");


        Object response = apiClient.deletePeriod(newPeriod.getPeriodId());
        //assertTrue(!response.isEmpty());
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.info(" Period deleted response for unitId = " + applicationProperties.getUnitId() + ":");
        log.info(" Response: " + response);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<");


        log.info("Send Application Close Event");
        context.publishEvent(new ContextClosedEvent(context));
    }

    private void submitVelocityGroupsForPeriod(Period period, List<SprintReportIssue> deliveredIssues) throws IOException, URISyntaxException {
        //DateTime scopeFixDay = period.FromDate.AddDays(2);

        List<GroupItem> velocityGroupItems = deliveredIssues.stream().map(x -> {
            GroupVelocityDeliveredItemsItem g = new GroupVelocityDeliveredItemsItem();

            // TODO fix
            g.setExternalId(String.valueOf(x.getId()));
            g.setEstimate(0.1);

            return g;
        }).collect(Collectors.toList());


        if (velocityGroupItems.size() > 0) {
            apiClient.submitGroupItems(period.getPeriodId(), velocityGroupItems);
            log.info("Velocity group items for period with external JIRA id = " + period.getName() + " were submitted successfully.");
        }
    }


    /// <summary>
    /// Here we define difference between periods that exist in our system and iteration (also period) that has been extracted from reporting tool.
    /// Only periods that previously were not uploaded to the system are submitted.
    /// </summary>
    /// <param name="submittedPeriods"> Periods that exist in the System. </param>
    /// <param name="iteration"> Period extracted from reporting tool. </param>
    /// <returns> Period in the System that current sprint belongs to. </returns>
    private Period SubmitPeriodIfRequired(Periods submittedPeriods, Sprint sprint) throws IOException, URISyntaxException {
        Period period = submittedPeriods.getPeriods().stream().filter(e -> e.getExternalId() == sprint.getId()).findFirst().orElse(null);

        if (period == null) {
            log.info("Submit period to api");
            period = new Period();

            period.setName(sprint.getName()); // required, could be very useful for debug purpose and to link periods submitted to API with periods in reporting tool
            period.setExternalId(sprint.getId()); // required, displayed on portal UI
            period.setFromDate(sprint.getStartDate()); // required, displayed on portal UI
            period.setToDate(sprint.getEndDate()); // reqired, displayed on portal UI

            period.setPeriodId(apiClient.submitPeriod(period).getPeriodId());
            log.info("Period with external JIRA id = " + period.getExternalId() + " was submitted successfully. Period was created with id = " + period.getPeriodId());
        }

        return period;
    }


    private static StreamDetails getStreamDetails() {
        // Delivery approach could be "EPAM Driven Hybrid", "Standalone", "TR Driven Hybrid", "Staff Aug"
        return new StreamDetails(
                "EPAM Driven",
                "John Doe",
                "John Doe",
                "Richard Roe",
                StreamDetails.StreamProcessType.Scrum
        );
    }

    /// Here you should define metrics that will be calculated for your stream
    /// You also need to define Min, Max, Warn and Error parameters. This parameters used to calculate status of the metric
    /// </summary>
    /// <returns> List of metrics for the stream</returns>
    private static List<MetricConfiguration> getActiveMetrics() {
        List<MetricConfiguration> metricConfigurations = new ArrayList<>();

        metricConfigurations.add(new MetricConfiguration(
                MetricType.Velocity,
                10, // error
                20, // warn
                50, // max
                0, // min
                "Storypoint" // unitOfMeasurement,
        ));
        metricConfigurations.add(new MetricConfiguration(
                MetricType.DeliveredVsPlanned,
                20, // error
                50, // warn
                40, // max
                0, // min
                "%" // unitOfMeasurement,
        ));
        metricConfigurations.add(new MetricConfiguration(
                MetricType.SpentVsEstimate,
                100, // error
                120, // warn
                200, // max
                0, // min
                "%" // unitOfMeasurement,
        ));
        metricConfigurations.add(new MetricConfiguration(
                MetricType.TestAutomationCoverage,
                20, // error
                50, // warn
                100, // max
                0, // min
                "%" // unitOfMeasurement,
        ));

        return metricConfigurations;
    }

    @Bean
    public GracefulShutdown jettyGracefulShutdown() {
        return new GracefulShutdown();
    }

    // Springboot closes application context before everything.
    private static class GracefulShutdown implements ApplicationListener<ContextClosedEvent> {

        private static final Logger log = LoggerFactory.getLogger(GracefulShutdown.class);

        @Override
        public void onApplicationEvent(ContextClosedEvent event) {
            log.info("Closing application");
            System.exit(0);
        }
    }

}
