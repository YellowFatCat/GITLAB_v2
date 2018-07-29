package com.epam.metrics.connectors.rest.api.models;

import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

/**
 * Created by Sergei_Zheleznov on 11.08.2017.
 */

@Getter
@Setter
public class ProgramDetails
{
    /// <summary>
    /// Process Type
    /// </summary>
    public enum ProgramProcessType
    {
        /// <summary>
        /// Scrum process type
        /// </summary>
        Scrum,

        /// <summary>
        /// Kanban process type
        /// </summary>
        Kanban,

        /// <summary>
        /// Project without estimations
        /// </summary>
        ProjectWithoutEstimations,

        /// <summary>
        /// Research and develpment
        /// </summary>
        ResearchAndDevelopment
    }

    /// <summary>
    /// Program delivery approach
    /// </summary>
    public String DeliveryApproach;

    /// <summary>
    /// Program delivery contact
    /// </summary>
    public String DeliveryContact;

    /// <summary>
    /// Program delivery manager
    /// </summary>
    public String DeliveryManager;

    /// <summary>
    /// Program lead
    /// </summary>
    public String ProgramLead;

    /// <summary>
    /// Program process type
    /// </summary>
    public ProgramProcessType ProcessType;

    /// <summary>
    /// Start date of a program
    /// </summary>
    public DateTime StartDate;

    /// <summary>
    /// Planned end date of a program
    /// </summary>
    public DateTime PlannedEndDate;
}