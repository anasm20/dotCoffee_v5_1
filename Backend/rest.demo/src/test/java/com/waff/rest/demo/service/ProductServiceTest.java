package com.waff.rest.demo.service;

import com.waff.rest.demo.config.StorageConfig;
import com.waff.rest.demo.model.Product;
import com.waff.rest.demo.model.ProductFilter;
import com.waff.rest.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProducts() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        assertTrue(productService.getProducts().isEmpty());
    }

    @Test
    void getProductById() {
        when(productRepository.findById("1")).thenReturn(Optional.of(new Product()));
        assertTrue(productService.getProductById("1").isPresent());
    }

    @Test
    void deleteProductById() {
        when(productRepository.findById("1")).thenReturn(Optional.of(new Product()));
        assertTrue(productService.deleteProductById("1"));
        verify(productRepository, times(1)).deleteById("1");
    }

    @Test
    void deleteProducts() {
        productService.deleteProducts();
        verify(productRepository, times(1)).deleteAll();
    }

    @Test
    void hasProducts() {
        when(productRepository.count()).thenReturn(1L);
        assertTrue(productService.hasProducts());
    }

    @Test
    void searchByFilter() {
        ProductFilter filter = new ProductFilter();
        filter.setTitel("test");
        filter.setCategory("category");

        when(productRepository.findByTitelIsContainingIgnoreCaseAndCategory_NameIsContainingIgnoreCase("test", "category"))
                .thenReturn(Collections.emptyList());

        assertTrue(productService.searchByFilter(filter).isEmpty());
    }
}
