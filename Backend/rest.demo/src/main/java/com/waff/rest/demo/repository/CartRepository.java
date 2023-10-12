package com.waff.rest.demo.repository;

import com.waff.rest.demo.model.Cart;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repository interface for performing database operations related to the Cart entity.
public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findByUser_Id(@NotBlank String userId);
}
