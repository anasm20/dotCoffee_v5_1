package com.waff.rest.demo.service;

import com.waff.rest.demo.model.Cart;
import com.waff.rest.demo.model.Product;
import com.waff.rest.demo.model.User;
import com.waff.rest.demo.repository.CartRepository;
import com.waff.rest.demo.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private StorageService storageService;

    @Mock
    private UserService userService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCarts() {
        when(cartRepository.findAll()).thenReturn(Collections.emptyList());
        assertTrue(cartService.getCarts().isEmpty());
    }

    @Test
    void getCartById() {
        when(cartRepository.findById("1")).thenReturn(Optional.of(new Cart()));
        assertTrue(cartService.getCartById("1").isPresent());
    }

    @Test
    void isCartEmpty() {
        Cart cart = new Cart();
        Product product = new Product();
        product.setId("2");
        cart.addItem(product);

        when(cartRepository.findByUser_Id("1")).thenReturn(Optional.of(cart));

        assertFalse(cartService.isCartEmpty("1"));
    }

    @Test
    void countItems() {
        Cart cart = new Cart();
        Product product = new Product();
        product.setId("2");
        cart.addItem(product);

        when(cartRepository.findByUser_Id("1")).thenReturn(Optional.of(cart));

        assertEquals(1, cartService.countItems("1"));
    }

}
