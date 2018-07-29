package com.epam.metrics.connectors.rest.api.models;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Sergei_Zheleznov on 11.08.2017.
 */
@Getter
@Setter
public class StreamDetails
{
    /// <summary>
    /// Represents stream process type.
    /// </summary>
    public enum StreamProcessType
    {
        /// <summary>
        /// Indicates that stream uses Scrum-like process.
        /// </summary>
        Scrum,

        /// <summary>
        /// Indicates that stream uses Kanban-like process.
        /// </summary>
        Kanban,

        /// <summary>
        /// Indicates that stream uses process without estimations.
        /// </summary>
        ProjectWithoutEstimations,

        /// <summary>
        /// Indicates that stream uses research and development methodology.
        /// </summary>
        ResearchAndDevelopment
    }

    public StreamDetails() {
    }

    public StreamDetails(String deliveryApproach, String deliveryContact, String deliveryManager, String streamLead, StreamProcessType processType) {
        DeliveryApproach = deliveryApproach;
        DeliveryContact = deliveryContact;
        DeliveryManager = deliveryManager;
        StreamLead = streamLead;
        this.processType = processType;
    }

    /// <summary>
    /// Gets or sets stream delivery approach.
    /// </summary>
    public String DeliveryApproach;

    /// <summary>
    /// Gets or sets stream delivery contact.
    /// </summary>
    public String DeliveryContact;

    /// <summary>
    /// Gets or sets stream delivery manager.
    /// </summary>
    public String DeliveryManager;

    /// <summary>
    /// Gets or sets stream lead.
    /// </summary>
    public String StreamLead;

    /// <summary>
    /// Gets or sets stream process type.
    /// </summary>
    public StreamProcessType processType;
}