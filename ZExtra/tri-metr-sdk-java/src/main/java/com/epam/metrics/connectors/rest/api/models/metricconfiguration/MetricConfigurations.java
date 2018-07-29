package com.epam.metrics.connectors.rest.api.models.metricconfiguration;

import java.util.ArrayList;
import java.util.List;

public class MetricConfigurations {
    private List<MetricConfiguration> metricConfigurations = new ArrayList<>();

    public List<MetricConfiguration> getMetricConfigurations() {
        return metricConfigurations;
    }

    public void setMetricConfigurations(List<MetricConfiguration> metricConfigurations) {
        this.metricConfigurations = metricConfigurations;
    }
}
