package com.waff.rest.demo.dto;

import com.waff.rest.demo.model.Product;
import com.waff.rest.demo.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public class CartDto {
    @NotNull
    private String id;

    private String userId;

    private List<Product> products;


    @Pattern(regexp = "(\\d+,\\d{1,2})")
     // Regular expression for validating costs with format: XX,XX where X is a digit.
    private String costs;


    public CartDto() {
    }

    // GETTER & SETTER

    public String getId() {
        return id;
    }

    public CartDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public CartDto setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public List<Product> getProducts() {
        return products;
    }

    public CartDto setProducts(List<Product> products) {
        this.products = products;
        return this;
    }

    public String getCosts() {
        // Gets the total costs of the products in the cart as a formatted string (XX,XX).
        if(products != null && !products.isEmpty()) {
            double dblTotal = products.stream()
                    .map(Product::getPrice)
                    .map(s -> s.replace(",", "."))
                    .mapToDouble(Double::parseDouble)
                    .sum();
            var strTotal = String.valueOf(dblTotal).replace(".", ",");
            setCosts(strTotal);
            return strTotal;
        }
        return "0,00";
    }

    // Sets the total costs of the products in the cart.
    public CartDto setCosts(String costs) {
        this.costs = costs;
        return this;
    }
}
