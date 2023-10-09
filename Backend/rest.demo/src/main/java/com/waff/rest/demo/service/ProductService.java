package com.waff.rest.demo.service;

import com.waff.rest.demo.model.Product;
import com.waff.rest.demo.model.ProductFilter;
import com.waff.rest.demo.repository.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    private final StorageService storageService;
    private final ProductRepository productRepository;

    public ProductService(StorageService storageService, ProductRepository productRepository) {
        this.storageService = storageService;
        this.productRepository = productRepository;
    }

    /**
     * Get all products from database
     * @return all products
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * Get product from database by giving productId
     * @param id
     * @return product
     */
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    /**
     * Create new product and save it to database
     * @param product product
     * @param image image of the product
     * @return new created product
     */
    public Optional<Product> createProduct(@Valid Product product, MultipartFile image) {
        if (product.getId() == null || !productRepository.existsById(product.getId())) {
            if(image != null){
                String filename = image.getOriginalFilename();
                String path = "/".concat(storageService.getStorageConfig().getLocation()).concat("/").concat(filename);
                product.setImagePath(path);
                storageService.storeDocument(image, filename);
            }
            var saved = productRepository.save(product);
            return Optional.of(saved);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Update an existing product from database by giving productId and new image of the product
     * @param product  product to update
     * @param image  new image of product
     * @return updated product
     */
    public Optional<Product> updateProduct(@Valid Product product, MultipartFile image) {
        var existingProduct = productRepository.findById(product.getId()).orElse(null);
        if(existingProduct != null) {
            var existingPath = existingProduct.getImagePath();
            if(image != null) {
                var filename = image.getOriginalFilename();
                String path = "/".concat(storageService.getStorageConfig().getLocation()).concat("/").concat(filename);
                product.setImagePath(path);
                storageService.storeDocument(image, filename);
                if (StringUtils.isNotBlank(existingPath))
                storageService.deleteDocument(existingPath);
            }else{
                product.setImagePath(existingPath);
            }
            var saved = productRepository.save(product);
            return Optional.of(saved);
        } else {
            return Optional.empty();
        }
    }

    /**
     * Delete Product by giving productId
     * @param id categoryId
     */
    public boolean deleteProductById(@NotBlank String id) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if(existingProduct != null) {
            productRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Delete all Products in database.
     */
    public void deleteProducts() {
        productRepository.deleteAll();
    }

    /**
     * Load image of the product from store directory
     * @param id productId
     * @return image as FileSystemResource
     */
    public Resource loadImage(String id) {
        Product existingCategory = productRepository.findById(id).orElse(null);
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
     * Check if database has product.
     */
    public boolean hasProducts() {
        return productRepository.count() > 0L;
    }

    /**
     *
     * @param filter
     * @return
     */
    public List<Product> searchByFilter(ProductFilter filter) {
        if(filter != null) {
            String titel = filter.getTitel();
            String category = filter.getCategory();
            if (StringUtils.isNotBlank(titel) || StringUtils.isNotBlank(category)) {
                if (StringUtils.isNotBlank(titel) && StringUtils.isBlank(category)) {
                    return productRepository.findByTitelIsContainingIgnoreCase(titel);
                }
                if (StringUtils.isBlank(titel) && StringUtils.isNotBlank(category)) {
                    return productRepository.findByCategory_NameIsContainingIgnoreCase(category);
                }
                return productRepository
                        .findByTitelIsContainingIgnoreCaseAndCategory_NameIsContainingIgnoreCase(titel, category);
            }
        }
        return List.of();
    }
}
