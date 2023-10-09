package com.waff.rest.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class StorageConfig {

    /**
     * Folder location for storing image files
     */
    @Value("${image.upload-directory}")
    private String location ;

    /**
     * Get Folder location for storing image files
     * @return Folder location
     */
    public String getLocation() {
        return location;
    }

    public Path getLocationPath() {
        return Path.of(location).toAbsolutePath().normalize();
    }

    public String getLocationPathPattern() {
        return "/" + location + "/**";
    }

    public String getResourceLocation() {
        return "file:/" + getLocationPath() + "/";
    }
}
