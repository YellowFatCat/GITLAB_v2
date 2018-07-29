package com.epam.metrics.example.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Sergei_Zheleznov on 22.08.2017.
 * application.yml
 *  application:
 *      jira:
 *          host...
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "application.jira", ignoreUnknownFields = false)
public class ApplicationProperties {

    private String jiraEndPoint;
    private String username;
    private String password;

    private String apiEndPoint;
    private Integer unitId;
    private String apiAccessToken;

    private Integer rapidViewId;
}
