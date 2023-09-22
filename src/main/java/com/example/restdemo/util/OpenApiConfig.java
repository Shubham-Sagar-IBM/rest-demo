package com.example.restdemo.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;

@Configuration
@Import(SwaggerUiConfigParameters.class)
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
                .group("user")
                .packagesToScan("com.example.restdemo.controller")
                .build();
    }
}
