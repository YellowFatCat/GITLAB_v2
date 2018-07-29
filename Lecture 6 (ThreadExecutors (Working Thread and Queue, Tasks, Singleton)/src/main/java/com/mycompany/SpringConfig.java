package com.mycompany;

import com.mycompany.swagger.SwaggerConfig;
import com.mycompany.swagger.SwaggerWebMvcConfigurerAdapter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource({
        "classpath:application.properties",
})
@ComponentScan(value = "com.mycompany.*")
@Import({
        SwaggerConfig.class,
        SwaggerWebMvcConfigurerAdapter.class
})
public class SpringConfig {
}
