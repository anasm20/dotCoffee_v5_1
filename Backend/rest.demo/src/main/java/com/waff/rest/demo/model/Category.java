package com.waff.rest.demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Entity(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id", nullable = false)
    private String id;

    @Size(min=2, max=64)
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "image_path")
    private String imagePath;


    public Category() {
    }

    public String getId() {
        return id;
    }

    public Category setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Category setName(String name) {
        this.name = name;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Category setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }
}
