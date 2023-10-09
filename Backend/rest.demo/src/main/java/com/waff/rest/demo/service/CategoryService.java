package com.waff.rest.demo.service;

import com.waff.rest.demo.model.Category;
import com.waff.rest.demo.dto.CategoryDto;
import com.waff.rest.demo.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {

    private final StorageService storageService;
    private final CategoryRepository categoryRepository;

    public CategoryService(StorageService storageService, CategoryRepository categoryRepository) {
        this.storageService = storageService;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Get all categories from database
     * @return all categories
     */
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Get category from database by giving categoryId
     * @param id category
     * @return category
     */
    public Optional<Category> getCategoryById(String id) {
        return categoryRepository.findById(id);
    }

    /**
     * Create new Category and save it to database
     * @param category category
     * @return  new created category
     */
    public Optional<Category> createCategory(@Valid Category category, MultipartFile image) {

        if (!categoryRepository.existsByName(category.getName())) {
            if(image != null){
                String filename = image.getOriginalFilename();
                String path = "/".concat(storageService.getStorageConfig().getLocation()).concat("/").concat(filename);
                category.setImagePath(path);
                storageService.storeDocument(image, filename);
            }
            var saved = categoryRepository.save(category);
            return Optional.of(saved);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Update an existing category by giving categoryId and new content of the category
     * @param category  category to update
     * @return updated category
     */
    public Optional<Category> updateCategory(@Valid Category category, MultipartFile image) {
        Category existingcategory = categoryRepository.findById(category.getId()).orElse(null);
        if(existingcategory != null) {
            var existingPath = existingcategory.getImagePath();
            if(image != null) {
                String filename = image.getOriginalFilename();
                String path = "/".concat(storageService.getStorageConfig().getLocation()).concat("/").concat(filename);
                category.setImagePath(path);
                storageService.storeDocument(image, filename);
                storageService.deleteDocument(existingPath);
            }
            Category saved = categoryRepository.save(category);
            return Optional.of(saved);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Delete Category by giving categoryId
     * @param id categoryId
     */
    public boolean deleteCategoryById(@NotBlank String id) {
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if(existingCategory != null) {
            categoryRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Delete all Categories.
     */
    public void deleteCategories() {
        categoryRepository.deleteAll();
    }

    /**
     * Load image from Directory
     * @param id
     * @return
     */
    public Resource loadResource(String id) {
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if(existingCategory != null) {
            try {
                return storageService.retrieveDocument(existingCategory.getImagePath());
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Check if database has category.
     */
    public boolean hasCategories() {
        return categoryRepository.count() > 0L;
    }
}
