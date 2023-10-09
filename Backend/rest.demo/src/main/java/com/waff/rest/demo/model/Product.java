package com.waff.rest.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id", nullable = false)
    private String id;

    @Size(min=2, max=64)
    @Column(name = "titel")
    private String titel;

    @Size(min=2, max=250)
    @Column(name = "description")
    private String description;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "price")
    private String price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "quantity", nullable = true)
    @PositiveOrZero
    private int quantity;

    public String getId() {
        return id;
    }

    public Product setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitel() {
        return titel;
    }

    public Product setTitel(String titel) {
        this.titel = titel;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Product setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public Product setPrice(String price) {
        this.price = price;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Product setCategory(Category category) {
        this.category = category;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public String toString() {
        return getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return new EqualsBuilder().append(id, product.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }
}
