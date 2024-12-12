package com.dn.shop.model.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateCategoryDTO {
    
    @NotNull(message = "Category name cannot be null")
    @Size(min = 1, message = "Category name must not be empty")
    private String name; // The name of the category

    private String description; // The description of the category (optional)

    // Getters and Setters
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