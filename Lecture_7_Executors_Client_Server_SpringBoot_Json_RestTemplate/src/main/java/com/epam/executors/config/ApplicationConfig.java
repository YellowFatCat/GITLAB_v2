package com.epam.executors.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({
        "classpath:spring-config/application.properties",
})
@ComponentScan("com.epam.executors.controller")
@Import({
        SwaggerConfig.class
})
public class ApplicationConfig {
}
