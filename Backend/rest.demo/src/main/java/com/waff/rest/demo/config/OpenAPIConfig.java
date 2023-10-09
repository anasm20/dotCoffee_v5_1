package com.waff.rest.demo.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class OpenAPIConfig {

    @Value("${project.version}")
    String version;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("dotCoffee Backend API")
                        .description("dotCoffee Backend Application Server")
                        .version(version));
    }
}
