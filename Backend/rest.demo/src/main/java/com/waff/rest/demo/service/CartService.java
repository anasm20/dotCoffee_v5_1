package com.waff.rest.demo.service;

import com.waff.rest.demo.model.Cart;
import com.waff.rest.demo.model.Product;
import com.waff.rest.demo.model.User;
import com.waff.rest.demo.repository.CartRepository;
import com.waff.rest.demo.repository.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    private final StorageService storageService;
    private final UserService userService;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    
    public CartService(StorageService storageService, UserService userService, CartRepository cartRepository,
                       ProductRepository productRepository) {
        this.storageService = storageService;
        this.userService = userService;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    /**
     * Get all Carts from database
     * @return all carts
     */
    public List<Cart> getCarts() {
        return cartRepository.findAll();
    }

    /**
     * Get cart from database by giving cartId
     * @param id
     * @return cart
     */
    public Optional<Cart> getCartById(String id) {
        return cartRepository.findById(id);
    }


    /**
     * Get cart from database by giving cartId
     * @param userId userId
     * @return cart of the userId
     */
    public Optional<Cart> getCartByUserId(String userId) {
        Cart cart = cartRepository.findByUser_Id(userId).orElse(null);
        if(cart == null) {
            return createCart(userId);
        }
        return Optional.of(cart);
    }

    /**
     * init new cart for the user and save it to database
     * @param userId userId
     * @return new created cart
     */
    public Optional<Cart> createCart(@NotBlank String userId) {
        if (userService.existUserById(userId)) {
            Cart cart = new Cart().setUser(new User().setId(userId));
            cartRepository.save(cart);
            return cartRepository.findById(cart.getId());
        }
        return Optional.empty();
    }

    /**
     * init new cart for the user and save it to database
     * @param cartId cartId
     * @return new created cart
     */
    // adds an item from the user's cart and saves the updated cart to the database.
    public Optional<Cart> addItemToCart(@NotBlank String cartId, @NotBlank String productId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);
        if(cart != null && product != null) {
            cart.addItem(product);
            Cart saved = cartRepository.save(cart);
            return Optional.of(saved);
        }
        return Optional.empty();
    }
// Removes an item from the user's cart and saves the updated cart to the database.
    public Optional<Cart> removeItemToCart(@NotBlank String cartId, @NotBlank String productId) {
        Cart cart = cartRepository.findById(cartId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);
        if(cart != null && product != null) {
            cart.removeItem(product);
            Cart saved = cartRepository.save(cart);
            return Optional.of(saved);
        }
        return Optional.empty();
    }


    /**
     * Delete all Products in cart.
     */
    public Optional<Cart> clearCart(@NotBlank String cardId) {
        Cart cart = cartRepository.findById(cardId).orElse(null);
        if(cart != null) {
          cart.getItems().clear();
            Cart saved = cartRepository.save(cart);
            return Optional.of(saved);
        }
        return Optional.empty();
    }


    /**
     * Check if database has product.
     */
    public boolean isCartEmpty(@NotBlank String userId) {
        Cart cart = cartRepository.findByUser_Id(userId).orElse(null);
        if(cart != null) {
            return cart.getItems().isEmpty();
        }
        return false;
    }

    public long countItems(@NotBlank String userId) {
        Cart cart = cartRepository.findByUser_Id(userId).orElse(null);
        if(cart != null) {
            return cart.getItems().size();
        }
        return 0L;
    }
}
