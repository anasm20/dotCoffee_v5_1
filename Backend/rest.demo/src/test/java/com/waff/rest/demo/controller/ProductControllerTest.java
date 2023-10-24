package com.waff.rest.demo.controller;

import com.waff.rest.demo.dto.ProductDto;
import com.waff.rest.demo.model.Product;
import com.waff.rest.demo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private ModelMapper modelMapper; // ModelMapper hinzugefügt

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct() {
        ProductDto productDto = new ProductDto();
        Product product = new Product();

        when(modelMapper.map(any(), any())).thenReturn(product); // ModelMapper-Mock hinzugefügt
        when(productService.createProduct(any(), any())).thenReturn(Optional.of(product));

        ResponseEntity<Product> responseEntity = productController.createCategory(productDto, null);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(product, responseEntity.getBody());
    }

    @Test
    void updateProduct() {
        ProductDto productDto = new ProductDto();
        Product product = new Product();

        when(modelMapper.map(any(), any())).thenReturn(product); // ModelMapper-Mock hinzugefügt
        when(productService.updateProduct(any(), any())).thenReturn(Optional.of(product));

        ResponseEntity<Product> responseEntity = productController.updateProduct(productDto, null);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(product, responseEntity.getBody());
    }
}
