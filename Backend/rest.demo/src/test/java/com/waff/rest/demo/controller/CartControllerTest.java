package com.waff.rest.demo.controller;

import com.waff.rest.demo.model.Cart;
import com.waff.rest.demo.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCarts() {
        List<Cart> carts = new ArrayList<>();
        when(cartService.getCarts()).thenReturn(carts);

        ResponseEntity<List<Cart>> responseEntity = cartController.getCarts();

        assertEquals(ResponseEntity.ok(carts), responseEntity);
    }

    @Test
    void findCartById() {
        String cartId = "cart123";
        Cart cart = new Cart();
        when(cartService.getCartById(cartId)).thenReturn(Optional.of(cart));

        ResponseEntity<Cart> responseEntity = cartController.findCartById(cartId);

        assertEquals(ResponseEntity.ok(cart), responseEntity);
    }

    @Test
    void findCartByIdNotFound() {
        String cartId = "cart123";
        when(cartService.getCartById(cartId)).thenReturn(Optional.empty());

        ResponseEntity<Cart> responseEntity = cartController.findCartById(cartId);

        assertEquals(ResponseEntity.notFound().build(), responseEntity);
    }

    @Test
    void getCartByUserId() {
        String userId = "user123";
        Cart cart = new Cart();
        when(cartService.getCartByUserId(userId)).thenReturn(Optional.of(cart));

        ResponseEntity<Cart> responseEntity = cartController.getCartByUserId(userId);

        assertEquals(ResponseEntity.ok(cart), responseEntity);
    }

    @Test
    void getCartByUserIdNotFound() {
        String userId = "user123";
        when(cartService.getCartByUserId(userId)).thenReturn(Optional.empty());

        ResponseEntity<Cart> responseEntity = cartController.getCartByUserId(userId);

        assertEquals(ResponseEntity.notFound().build(), responseEntity);
    }

    @Test
    void addProductToCart() {
        String cartId = "cart123";
        String productId = "product456";
        Cart cart = new Cart();
        when(cartService.addItemToCart(cartId, productId)).thenReturn(Optional.of(cart));

        ResponseEntity<Cart> responseEntity = cartController.addProductToCart(cartId, productId);

        assertEquals(ResponseEntity.ok(cart), responseEntity);
    }

    @Test
    void addProductToCartNotFound() {
        String cartId = "cart123";
        String productId = "product456";
        when(cartService.addItemToCart(cartId, productId)).thenReturn(Optional.empty());

        ResponseEntity<Cart> responseEntity = cartController.addProductToCart(cartId, productId);

        assertEquals(ResponseEntity.notFound().build(), responseEntity);
    }

    @Test
    void removeProductToCart() {
        String cartId = "cart123";
        String productId = "product456";
        Cart cart = new Cart();
        when(cartService.removeItemToCart(cartId, productId)).thenReturn(Optional.of(cart));

        ResponseEntity<Cart> responseEntity = cartController.removeProductToCart(cartId, productId);

        assertEquals(ResponseEntity.ok(cart), responseEntity);
    }

    @Test
    void removeProductToCartNotFound() {
        String cartId = "cart123";
        String productId = "product456";
        when(cartService.removeItemToCart(cartId, productId)).thenReturn(Optional.empty());

        ResponseEntity<Cart> responseEntity = cartController.removeProductToCart(cartId, productId);

        assertEquals(ResponseEntity.notFound().build(), responseEntity);
    }

    @Test
    void clearCart() {
        String cartId = "cart123";
        Cart cart = new Cart();
        when(cartService.clearCart(cartId)).thenReturn(Optional.of(cart));

        ResponseEntity<Cart> responseEntity = cartController.clearCart(cartId);

        assertEquals(ResponseEntity.ok(cart), responseEntity);
    }

    @Test
    void clearCartNotFound() {
        String cartId = "cart123";
        when(cartService.clearCart(cartId)).thenReturn(Optional.empty());

        ResponseEntity<Cart> responseEntity = cartController.clearCart(cartId);

        assertEquals(ResponseEntity.badRequest().build(), responseEntity);
    }
}
