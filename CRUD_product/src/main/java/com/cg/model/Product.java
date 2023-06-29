package com.cg.model;

import java.time.LocalDate;

public class Product {
    private Long id;
    private String name;
    private int quantity;

    private LocalDate createAt;

    private ECategory category;

    public Product() {
    }

    public Product(Long id, String name, int quantity, LocalDate createAt, ECategory category) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.createAt = createAt;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public ECategory getCategory() {
        return category;
    }

    public void setCategory(ECategory category) {
        this.category = category;
    }
}
