package com.waff.rest.demo.service;

import com.waff.rest.demo.config.StorageConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class StorageService {

    private Path location;

    private static Logger logger = LoggerFactory.getLogger(StorageService.class);

    private final StorageConfig storageConfig;

    public StorageService(StorageConfig storageConfig, StorageConfig storageConfig1) throws IOException {
        this.location = Path.of(storageConfig.getLocation()).toAbsolutePath().normalize();
        this.storageConfig = storageConfig1;
        if(!Files.exists(this.location)) {
            Files.createDirectories(this.location);
        }
    }

    public void storeDocument(MultipartFile file, String name) {
        Path resolve = this.location.resolve(name);
        logger.info("File name: {}", file.getOriginalFilename());
        if(!Files.exists(resolve)) {
            try {
                Files.copy(file.getInputStream(), resolve);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Resource retrieveDocument(String name) throws IOException {
        Path resolve = this.location.resolve(name);
        return new FileSystemResource(resolve);
    }

    public void deleteDocument(String name)  {
        name = name.replaceFirst("/".concat(getStorageConfig().getLocation()).concat("/"), "");
        Path resolve = this.location.resolve(name);
        if(Files.exists(resolve)) {
            try {
                Files.delete(resolve);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Path getLocation() {
        return location;
    }

    public StorageConfig getStorageConfig() {
        return storageConfig;
    }
}
