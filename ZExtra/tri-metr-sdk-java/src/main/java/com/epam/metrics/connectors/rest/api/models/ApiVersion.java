package com.epam.metrics.connectors.rest.api.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiVersion {
    private String version;
    private String commitHash;
    private String branch;

    @Override
    public String toString() {
        return "ApiVersion{" +
                "version='" + version + '\'' +
                ", commitHash='" + commitHash + '\'' +
                ", branch='" + branch + '\'' +
                '}';
    }
}
