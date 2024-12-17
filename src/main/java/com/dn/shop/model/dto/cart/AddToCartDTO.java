package com.dn.shop.model.dto.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddToCartDTO {
    @NotNull(message = "Product name cannot be null")
    @Size(min = 1, message = "Product name must not be empty")
    private String productName;

    @NotNull(message = "Quantity cannot be null")
    private Integer quantity = 1; // Default to 1 if not specified

    // Getters and Setters
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
} 