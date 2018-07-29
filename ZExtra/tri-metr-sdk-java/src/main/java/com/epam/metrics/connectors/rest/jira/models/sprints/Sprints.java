package com.epam.metrics.connectors.rest.jira.models.sprints;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sergei_Zheleznov on 23.08.2017.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Sprints {
    volatile private List<Sprint> sprints;

    public List<Sprint> getSprints() {
        if (sprints == null) {
            sprints = Collections.synchronizedList(new ArrayList<>());
        }

        return sprints;
    }

    public void setSprints(List<Sprint> sprints) {
        this.sprints = sprints;
    }
}
