package com.dn.shop.model.dto.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class CreateProductDTO {
    
    @NotNull(message = "Product name cannot be null")
    @Size(min = 3, message = "Product name must be at least 3 characters long")
    private String name; // Product name

    @NotNull(message = "Description cannot be null")
    private String description; // Product description

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be a positive number")
    private BigDecimal price; // Product price

    @NotNull(message = "Stock quantity cannot be null")
    @PositiveOrZero(message = "Stock quantity must be a non-negative integer")
    private int stock; // Initial stock quantity

    @NotNull(message = "Category ID cannot be null")
    private Long categoryId; // The ID of the category this product belongs to

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
} 