package com.epam.executors;

import com.epam.executors.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableAutoConfiguration
@Import(ApplicationConfig.class)
public class ExecutorsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExecutorsApplication.class, args);
	}
}
