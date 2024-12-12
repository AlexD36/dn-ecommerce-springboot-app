package com.dn.shop.model.dto.product;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class UpdateProductDTO {
    
    @Size(min = 3, message = "Product name must be at least 3 characters long")
    private String name; // Updated product name (optional)

    private String description; // Updated product description (optional)

    @Positive(message = "Price must be a positive number")
    private BigDecimal price; // Updated product price (optional)

    @PositiveOrZero(message = "Stock quantity must be a non-negative integer")
    private Integer stock; // Updated stock quantity (optional)

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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
} 