package com.epam.metrics.connectors.rest.api.models.periods;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

/**
 * Created by Sergei_Zheleznov on 11.08.2017.
 */

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Period
{
    /// <summary>
    /// Gets or sets start date of the periods.
    /// </summary>
    //@JsonSerialize(using=CustomerDateSerializer.class)
    //@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ")
    //@JsonDeserialize(using=DateTimeDeserializer.class)
    public DateTime fromDate;

    /// <summary>
    /// Gets or sets end date of the periods.
    /// </summary>

    public DateTime toDate;

    /// <summary>
    /// Gets or sets periods name (displayed on ui).
    /// </summary>
    public String name;

    /// <summary>
    /// Gets or sets periods external id (JIRA sprint id, TFS iteration path etc.). This field help to map API periods to any external during debug process.
    /// </summary>
    public Integer externalId;

    /// <summary>
    /// Gets or sets periods id. This value is used to submit any related to periods data (groups).
    /// </summary>
    public int periodId;

    public Integer parentPeriodId;

    public Integer unitId;

    public Integer periodType;

    public String state;

    public String status;

    @Override
    public String toString() {
        return "Period{" +
                "fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", name='" + name + '\'' +
                ", externalId='" + externalId + '\'' +
                ", periodId=" + periodId +
                ", parentPeriodId=" + parentPeriodId +
                ", unitId=" + unitId +
                ", periodType=" + periodType +
                ", state='" + state + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}