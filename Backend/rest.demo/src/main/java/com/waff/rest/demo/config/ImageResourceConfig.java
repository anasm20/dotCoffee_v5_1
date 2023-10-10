package com.waff.rest.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Configuraiton class for handling image resources in the web application

@Configuration
public class ImageResourceConfig implements WebMvcConfigurer {

     // Storage configuration for image resources
    public final StorageConfig storageConfig;

    // Constructor to initialize ImageResourceConfig with StorageConfig dependency injection.
    public ImageResourceConfig(StorageConfig storageConfig) {
        this.storageConfig = storageConfig;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Adds a resource handler for the specified location path pattern and resource location
        registry
                .addResourceHandler(storageConfig.getLocationPathPattern())
                .addResourceLocations(storageConfig.getResourceLocation());
    }
}
