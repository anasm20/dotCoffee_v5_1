package com.waff.rest.demo.controller;

import com.waff.rest.demo.model.Cart;
import com.waff.rest.demo.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Controller class handling cart-related endpoints in the webshop backend.
@RestController
@CrossOrigin("*")
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @GetMapping
    // GET request to retrieve all carts
    public ResponseEntity<List<Cart>> getCarts() {
        return ResponseEntity.ok(cartService.getCarts());
    }


    @GetMapping( "/{card_id} ")
    // GET request to find a cart by its id
    public ResponseEntity<Cart> findCartById(@PathVariable("card_id") String  cartId) {
        Cart cart = cartService.getCartById(cartId).orElse(null);
        if(cart != null) {
            return ResponseEntity.ok(cart);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping( "/user/{user_id}")
    // GET request to find a cart by user id
    public ResponseEntity<Cart> getCartByUserId(@PathVariable("user_id") String  userId) {
        Cart cart = cartService.getCartByUserId(userId).orElse(null);
        if(cart != null) {
            return ResponseEntity.ok(cart);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping( "/{cartId}/product/{productId}/add")
    // Handles POST request to add a product to the cart.
    public ResponseEntity<Cart> addProductToCart(@PathVariable String  cartId, @PathVariable String productId) {
        Cart cart = cartService.addItemToCart(cartId, productId).orElse(null);
        if(cart != null) {
            return ResponseEntity.ok(cart);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping( "/{cartId}/product/{productId}/remove")
    // Handles POST request to remove a product from the cart.
    public ResponseEntity<Cart> removeProductToCart(@PathVariable String  cartId, @PathVariable String productId) {
        Cart cart = cartService.removeItemToCart(cartId, productId).orElse(null);
        if(cart != null) {
            return ResponseEntity.ok(cart);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping( "/{cartId}/clear")
    // Handles POST request to clear the cart.
        public ResponseEntity<Cart> clearCart(@PathVariable String  cartId) {
            Cart cart = cartService.clearCart(cartId).orElse(null);
        if(cart != null) {
            return ResponseEntity.ok(cart);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping( "/product/count")
    // Handles GET request to count the number of products in the cart.
    public ResponseEntity<Cart> countProductInCart(@PathVariable String  cartId) {
        /*Cart cart = cartService.countItems(cartId).orElse(null);
        if(cart != null) {
            return ResponseEntity.ok(cart);
        }*/
        return ResponseEntity.badRequest().build();
    }
}
