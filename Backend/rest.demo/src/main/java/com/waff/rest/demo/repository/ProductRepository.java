package com.waff.rest.demo.repository;

import com.waff.rest.demo.model.Category;
import com.waff.rest.demo.model.Product;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//  Repository interface for performing database operations related to the Product entity.
// Das ProductRepository Interface ist eine JPA-Repository-Schnittstelle, die mit der Datenbank kommuniziert
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByTitelIsContainingIgnoreCase(@NotBlank String titel);
    List<Product> findByCategory_Id(@NotBlank String categoryId);
    List<Product> findByCategory_NameIsContainingIgnoreCase(@NotBlank String categoryName);
    List<Product> findByTitelIsContainingIgnoreCaseAndCategory_NameIsContainingIgnoreCase(@NotBlank String titel, @NotBlank String category);
}
