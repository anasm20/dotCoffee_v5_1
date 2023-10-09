package com.waff.rest.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.util.*;

@Entity(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cart_id", nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @ManyToMany
    @JoinTable(
        name = "cart_items",
        joinColumns = @JoinColumn(name = "cart_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> items = new HashSet<>();

    @Column(name = "costs")
    private String costs;


    public Cart() {
    }

    public String getId() {
        return id;
    }

    public Cart setId(String id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Cart setUser(User user) {
        this.user = user;
        return this;
    }

    public Set<Product> getItems() {
        return items;
    }

    public Cart setItems(Set<Product> items) {
        this.items = items;
        return this;
    }

    public String getCosts() {
        /*if(products != null && !products.isEmpty()) {
            double dblTotal = products.stream()
                    .map(Product::getPrice)
                    .map(s -> s.replace(",", "."))
                    .mapToDouble(Double::parseDouble)
                    .sum();
            var strTotal = String.valueOf(dblTotal).replace(".", ",");
            setCosts(strTotal);
            return strTotal;
        }*/
        return "0,00";
    }

    public Cart setCosts(String costs) {
        this.costs = costs;
        return this;
    }

    public void addItem(Product product) {
        if(items!= null && items.contains(product)) {
            Product match = items.stream().filter(p -> p.getId().equals(product.getId())).findFirst().orElse(null);
            if(match != null) {
                match.setQuantity(match.getQuantity() + 1);
                items.add(match);
            }
        } else {
            product.setQuantity(1);
            items.add(product);
        }

    }


    public void removeItem(Product product) {
        if(items!= null && items.contains(product)) {
            Product match = items.stream().filter(p -> p.getId().equals(product.getId())).findFirst().orElse(null);
            if(match != null) {
                match.setQuantity(match.getQuantity() - 1);
                if(match.getQuantity() <= 0) {
                    items.remove(match);
                } else {
                    items.add(match);
                }
            }
        }
    }
}
