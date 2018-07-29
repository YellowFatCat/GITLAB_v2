package com.epam.metrics.connectors.rest.jira.models.boards;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BoardsResponse {

    private List<Board> boards;
}
