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
    private String costs;


    public CartDto() {
    }

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

    public CartDto setCosts(String costs) {
        this.costs = costs;
        return this;
    }
}
