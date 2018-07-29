package com.epam.metrics.connectors.rest.api;

import com.epam.metrics.connectors.rest.ICredentials;
import com.epam.metrics.connectors.rest.api.helper.RestClient;
import com.epam.metrics.connectors.rest.api.models.*;
import com.epam.metrics.connectors.rest.api.models.groups.GroupItem;
import com.epam.metrics.connectors.rest.api.models.metricconfiguration.MetricConfiguration;
import com.epam.metrics.connectors.rest.api.models.metricconfiguration.MetricConfigurations;
import com.epam.metrics.connectors.rest.api.models.metricconfiguration.MetricType;
import com.epam.metrics.connectors.rest.api.models.periods.Period;
import com.epam.metrics.connectors.rest.api.models.periods.Periods;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static com.epam.metrics.connectors.rest.jira.helpers.HttpClientHelper.buildHttpClient;
import static com.epam.metrics.connectors.rest.jira.helpers.JiraUtil.validateResponse;

public class ApiClient {
    private RestClient restClient;
    private ICredentials creds;
    private Integer unitId;

    public ApiClient(String endpoint, ICredentials creds, Integer unitId) {
        this(null, endpoint, creds, unitId);
    }



    public ApiClient(HttpClient httpClient, String endpoint, ICredentials creds, Integer unitId) {
        this.unitId = unitId;

        if (httpClient == null) {
            httpClient = buildHttpClient();
        }

        restClient = new RestClient(httpClient, creds, URI.create(endpoint));
    }

    /// <summary>
    /// Submits stream details to API. Replaces any existing stream details.
    /// </summary>
    /// <param name="details">Stream details object.</param>
    public void submitStreamDetails(StreamDetails details) throws IOException, URISyntaxException {
        if (details == null)
        {
            throw new IllegalArgumentException("StreamDetails is null");
        }

        restClient.put("/units/" + unitId + "/streamdetails" , details, Object.class);

        //TODO: check that response code is HttpStatus.OK
    }

    /// <summary>
    /// Submits program details to API. Replaces any existing program details.
    /// </summary>
    /// <param name="details">Program details object.</param>
    public void submitProgramDetails(ProgramDetails details) throws URISyntaxException, IOException {
        if (details == null)
        {
            throw new IllegalArgumentException("ProgramDetails is null");
        }

        restClient.put("/units/" + unitId + "/programdetails" , details, Object.class);

        //TODO: check that response code is HttpStatus.OK
    }

    /// <summary>
    /// Gets collection of current metrics' configurations.
    /// </summary>
    /// <returns>Collection of mertics' configurations.</returns>
    // public List<MetricConfiguration> getMetricConfigurations() throws URISyntaxException, IOException, RestException {
    public MetricConfigurations getMetricConfigurations() throws URISyntaxException, IOException {

        ResponseEntity<MetricConfigurations> response = restClient.get("/units/" + unitId + "/metrics/configurations", MetricConfigurations.class);

        return validateResponse(response, HttpStatus.OK);
    }


    /// <summary>
    /// Submits metrics' configurations for stream. Replaces old configurations.
    /// </summary>
    /// <param name="metricsConfigurations">Collection of mertics' configurations.</param>
    public Void submitMetricsConfiguration(List<MetricConfiguration> metricsConfigurations) throws IOException, URISyntaxException {

        if (CollectionUtils.isEmpty(metricsConfigurations))
        {
            throw new IllegalArgumentException("MetricsConfigurations are empty or null");
        }

        List<MetricConfiguration> submittedConfigurations = getMetricConfigurations().getMetricConfigurations();

        for (Object configurationToRemove : submittedConfigurations) {

            MetricConfiguration metricConfiguration = (MetricConfiguration) configurationToRemove;
            MetricType metricType = metricConfiguration.getMetricType();

            // TODO: May be this delete request contains mistake
            ResponseEntity<Void> response = restClient.delete("/units/" + unitId + "/metrics/" + metricType + "/configuration", Void.class);

            //TODO: check that response code is HttpStatus.OK
            return validateResponse(response, HttpStatus.OK);
        }

        for (MetricConfiguration configuration :  metricsConfigurations) {
            MetricType metricType = configuration.getMetricType();

            ResponseEntity<Void> response = restClient.post("/units/" + unitId + "/metrics/" + metricType + "/configuration" , configuration,  Void.class);

            //TODO: check that response code is HttpStatus.Created
            return validateResponse(response, HttpStatus.CREATED);
        }

        return null;
    }

    /// <summary>
    /// Gets already submitted periods with the given external id.
    /// </summary>
    /// <param name="externalId">External id of the periods.</param>
    /// <returns>Period object.</returns>
    public Period getPeriodByExternalId(String externalId) throws URISyntaxException, IOException {
        if (StringUtils.isEmpty(externalId))
        {
            throw new IllegalArgumentException("ExternalId is null or empty");
        }

        Map<String, String> params = new HashMap<>();
        params.put("externalId", externalId);

        ResponseEntity<Periods> response = restClient.get("/units/" + unitId + "/periods", params, Periods.class);

        Periods periods = validateResponse(response, HttpStatus.OK);
        return  periods.getPeriods().get(0);
    }


    /// <summary>
    /// Submits new periods to metrics.
    /// </summary>
    /// <param name="periods">Period to submit.</param>
    /// <returns>Id of created periods.</returns>

    public Period submitPeriod(Period period) throws IOException, URISyntaxException {
        if (period == null) {
            throw new IllegalArgumentException("Period is null");
        }

        ResponseEntity<Period> response = restClient.post("/units/" + unitId + "/periods/", period, Period.class);
        return validateResponse(response, HttpStatus.OK, HttpStatus.CREATED);
    }

    public Periods getPeriods() throws URISyntaxException, IOException {
        Map<String, String> params = new HashMap<>();
        params.put("includeNonClosedPeriods", "true");

        ResponseEntity<Periods> response = restClient.get("/units/" + unitId + "/periods", params, Periods.class);

        return validateResponse(response, HttpStatus.OK);
    }

    /**
     * Deletes periods and all related to periods data (calculated metrics, groups).
     * @param periodId Id of periods to delete
     * @return ???
     * @throws URISyntaxException
     * @throws IOException
     */
    public Void deletePeriod(int periodId) throws URISyntaxException, IOException {

        ResponseEntity<Void> response = restClient.delete("/units/" + unitId + "/periods/" + periodId, Void.class);

        return validateResponse(response, HttpStatus.OK, HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes all periods and all related data (calculated metrics, groups).
     * @throws URISyntaxException
     * @throws IOException
     */
    public Void deletePeriods() throws URISyntaxException, IOException {

        ResponseEntity<Void> response = restClient.delete("/units/" + unitId + "/periods/", Void.class);

        return validateResponse(response, HttpStatus.OK, HttpStatus.NO_CONTENT);
    }

    /**
     * No authorization required for this method
     * @return string
     * @throws URISyntaxException
     * @throws IOException
     */
    public ApiVersion getVersion() throws URISyntaxException, IOException {

        ResponseEntity<ApiVersion> response = restClient.delete("/units/" + unitId + "/periods/", ApiVersion.class);

        return validateResponse(response, HttpStatus.OK, HttpStatus.NO_CONTENT);
    }

    /**
     * Allows to check whether groups items with the given type and periods are submitted.
     * @param periodId Period id to check
     * @param group - string repr of group type
     * @return Boolean value indicating whether groups items submitted or not
     * @throws URISyntaxException
     * @throws IOException
     */
    public Boolean areGroupItemsSubmitted (int periodId, String group) throws URISyntaxException, IOException {

        ResponseEntity<Boolean> response = restClient.get("/units/" + unitId + "/periods/" + periodId + "/groups/" + group + "/count", Boolean.class);

        return validateResponse(response, HttpStatus.OK);
    }

    /**
     * Allows to check whether groups items with the given type and periods are submitted.
     * @param periodId Period id to check
     * @param GroupClass
     * @return Boolean value indicating whether groups items submitted or not
     * @throws URISyntaxException
     * @throws IOException
     */
    public Boolean areGroupItemsSubmitted (int periodId, Class<? extends GroupItem> GroupClass) throws URISyntaxException, IOException {
        String group = "unknown";
        try {
            group = GroupClass.newInstance().getGroupItemType();
        } catch (IllegalAccessException|InstantiationException e) {
            e.printStackTrace();
        }
        return areGroupItemsSubmitted(periodId, group);
    }

    /**
     * Submits groups of items of given type for the given periods. Replaces any previously submitted items.
     * Note: replace is allowed only for future periods and periods which are in progress.
     * @param periodId Period id of groups
     * @param items Group items to submit
     * @throws IOException
     * @throws URISyntaxException
     */
    public void submitGroupItems(int periodId, List<GroupItem> items) throws IOException, URISyntaxException {
        if (CollectionUtils.isEmpty(items))
        {
            throw new IllegalArgumentException("Group Items are empty or null");
        }

        Boolean submitted = areGroupItemsSubmitted(periodId, items.get(0).getClass());
        String groups = items.get(0).getGroupItemType();

        boolean isUpdate =  submitted != null && submitted;

        Map<String, String> params = new HashMap<>();
        Object response = null;

        if (isUpdate) {
            response = restClient.put("/units/" + unitId + "/periods/" + periodId + "/groups/" + groups , items, Object.class);
        } else {
            response = restClient.post("/units/" + unitId + "/periods/" + periodId + "/groups/" + groups , items, Object.class);
        }
        //TODO: check response code, should be HttpStatusCode.OK if isUpdate or HttpStatusCode.Created otherwise
    }

}
