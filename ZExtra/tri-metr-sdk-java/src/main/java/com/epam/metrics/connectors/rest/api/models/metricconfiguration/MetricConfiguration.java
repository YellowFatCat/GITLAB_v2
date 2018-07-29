package com.epam.metrics.connectors.rest.api.models.metricconfiguration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Sergei_Zheleznov on 11.08.2017.
 */

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetricConfiguration
{
    /// <summary>
    /// Gets or sets type of configured metric.
    /// </summary>
    public MetricType metricType;

    /// <summary>
    /// Gets or sets error threshold for metric.
    /// </summary>
    public double error;

    /// <summary>
    /// Gets or sets warn threshold for metric.
    /// </summary>
    public double warn;

    /// <summary>
    /// Gets or sets max applicable value for metric.
    /// </summary>
    public double max;

    /// <summary>
    /// Gets or sets min applicable value for metric.
    /// </summary>
    public double min;

    /// <summary>
    /// Gets or sets metric unit of measurement.
    /// </summary>
    public String unitOfMeasurement;

    /// <summary>
    /// Gets or sets dispaly name for a metric
    /// </summary>
    public String displayName;

    /// <summary>
    /// Gets or sets format to diplay metric value
    /// Following formats will be used in UI <see href="https://www.npmjs.com/package/sprintf-js">Sprintf-js lib</see>
    /// </summary>
    public String format;

    /// <summary>
    /// Gets or sets description of a metric
    /// </summary>
    public String description;

    public MetricConfiguration() {
    }

    public MetricConfiguration(MetricType metricType, double error, double warn, double max, double min, String unitOfMeasurement) {
        this(metricType, error, warn, max, min, unitOfMeasurement, null, null, null);
    }

    public MetricConfiguration(MetricType metricType, double error, double warn, double max, double min, String unitOfMeasurement, String displayName, String format, String description) {
        this.metricType = metricType;
        this.error = error;
        this.warn = warn;
        this.max = max;
        this.min = min;
        this.unitOfMeasurement = unitOfMeasurement;
        this.displayName = displayName;
        this.format = format;
        this.description = description;
    }

    @Override
    public String toString() {
        return "MetricConfiguration{" +
                "metricType=" + metricType +
                ", error=" + error +
                ", warn=" + warn +
                ", max=" + max +
                ", min=" + min +
                ", unitOfMeasurement='" + unitOfMeasurement + '\'' +
                ", displayName='" + displayName + '\'' +
                ", format='" + format + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}