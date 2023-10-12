package com.waff.rest.demo.controller;

import com.waff.rest.demo.model.Category;
import com.waff.rest.demo.dto.CategoryDto;
import com.waff.rest.demo.service.CategoryService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//  Controller class handling category-related endpoints in the webshop backend.
@RestController
@CrossOrigin("*")
//in Spring Boot erlaubt kontrolliert, dass Webbrowser-Anfragen von einer bestimmten Domain zu Ihrer Anwendung gesendet werden können, 
// indem es Cross-Origin Resource Sharing (CORS) konfiguriert und die Sicherheit Ihrer Anwendung bewahrt.
public class CategoryController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/category")
    // Handles GET request to retrieve all categories.
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }


    @PostMapping(value = "/admin/category", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    // Handles POST request to create a new category.
    public ResponseEntity<Category> createCategory(@Valid @ModelAttribute CategoryDto categoryDto, BindingResult result) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category created = categoryService.createCategory(category, categoryDto.getImage()).orElse(null);
        if(created != null) {
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/category/{id}")
    // Handles GET request to retrieve a category by its ID.
    public ResponseEntity<?> getCategoryById(@PathVariable String id) {
        Category category = categoryService.getCategoryById(id).orElse(null);
        if(category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping(value = "/admin/category/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    // Handles PUT request to update an existing category.
    public ResponseEntity<Category> updateCategory(@Valid @ModelAttribute CategoryDto categoryDto, BindingResult result) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category updated = categoryService.updateCategory(category, categoryDto.getImage()).orElse(null);
        if(updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/admin/category/{id}")
    // Handles DELETE request to delete a category by its ID.
    public ResponseEntity<Void> deleteCategoryById(@PathVariable String id) {
        if(categoryService.deleteCategoryById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/admin/category/")
    // Handles DELETE request to delete a category
    public ResponseEntity<Void> deleteCategories() {
        categoryService.deleteCategories();
        return ResponseEntity.ok().build();
    }
}
