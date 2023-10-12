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

    // Gets the absolute path of the folder location for storing image files.
    public Path getLocationPath() {
        return Path.of(location).toAbsolutePath().normalize();
    }
    // Gets the pattern for mapping URLs to the stored image files.
    public String getLocationPathPattern() {
        return "/" + location + "/**";
    }
    // Gets the resource location URL for accessing stored image files.
    public String getResourceLocation() {
        // Constructs a resource location URL using the absolute path of the storage location
        return "file:/" + getLocationPath() + "/";
    }
}
