package com.dn.shop.model.dto;

public class CategoryDTO {
    private Long id; // Unique identifier of the category
    private String name; // The name of the category
    private String description; // The description of the category

    // Getters and Setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
} 