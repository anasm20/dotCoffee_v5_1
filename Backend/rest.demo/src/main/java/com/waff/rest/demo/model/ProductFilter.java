package com.waff.rest.demo.model;

public class ProductFilter {

    private String titel;

    private String category;

    public ProductFilter() {
    }

    public String getTitel() {
        return titel;
    }

    public ProductFilter setTitel(String titel) {
        this.titel = titel;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public ProductFilter setCategory(String category) {
        this.category = category;
        return this;
    }
}
