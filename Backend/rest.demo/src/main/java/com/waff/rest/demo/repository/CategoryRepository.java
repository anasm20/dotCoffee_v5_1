package com.waff.rest.demo.repository;

import com.waff.rest.demo.model.Category;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> findById(@NotBlank String id);
    Optional<Category> findByName(@NotBlank String name);
    boolean existsByName(@NotBlank String name);
}
