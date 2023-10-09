package com.waff.rest.demo.dto;

import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public class CategoryDto {

    private String id = UUID.randomUUID().toString();

    @NotBlank
    private String name;

    private MultipartFile image;

    private String imagePath;

    public CategoryDto() {
    }

    public String getId() {
        return id;
    }

    public CategoryDto setId(String id) {
        if(StringUtils.isNotBlank(id)) {
            this.id = id;
        }
        return this;
    }

    public String getName() {
        return name;
    }

    public CategoryDto setName(String name) {
        this.name = name;
        return this;
    }

    public MultipartFile getImage() {
        return image;
    }

    public CategoryDto setImage(MultipartFile image) {
        this.image = image;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public CategoryDto setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }
}
