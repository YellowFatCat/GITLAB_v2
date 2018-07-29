package com.epam.metrics.connectors.rest.jira.models.boards;

/// <summary>
/// Represents JIRA board information.
/// </summary>

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Sergei_Zheleznov on 23.08.2017.
 */
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Board {

    /// <summary>
    /// Gets or sets JIRA internal board id.
    /// </summary>
    public int id;

    /// <summary>
    /// Gets or sets JIRA board name.
    /// </summary>
    public String name;

    /// <summary>
    /// Gets or sets JIRA board type.
    /// </summary>
    public String type;
}
