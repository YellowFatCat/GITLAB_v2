package com.epam.metrics.connectors.rest.jira.models.sprints;

import com.epam.metrics.connectors.rest.jira.helpers.DateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

/**
 * Created by Sergei_Zheleznov on 22.08.2017.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sprint {

    /// <summary>
    /// Gets or sets JIRA internal sprint id.
    /// </summary>
    public int id;

    /// <summary>
    /// Gets or sets JIRA sprint state.
    /// </summary>
    public String state;

    /// <summary>
    /// Gets or sets JIRA sprint name.
    /// </summary>
    public String name;

    /// <summary>
    /// Gets or sets sprint start date. May not be available in some cases (sprint is not closed, JIRA version is old etc.)
    /// </summary>
    //@JsonFormat(pattern = "dd/MNM/YY HH:mm")
    @JsonDeserialize(using = DateTimeDeserializer.class)
    public DateTime startDate;

    /// <summary>
    /// Gets or sets sprint planned end date. May not be available in some cases (sprint is not closed, JIRA version is old etc.)
    /// </summary>
    @JsonDeserialize(using = DateTimeDeserializer.class)
    public DateTime endDate;

    /// <summary>
    /// Gets or sets sprint fact complete date. May not be available in some cases (sprint is not closed, JIRA version is old etc.)
    /// </summary>
    @JsonDeserialize(using = DateTimeDeserializer.class)
    public DateTime completeDate;

    public int daysRemaining;

    public int sequence;
}

