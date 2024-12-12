package com.dn.shop.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class UpdateCartItemDTO {
    
    @NotNull(message = "Cart item ID cannot be null")
    private Long cartItemId; // The ID of the cart item to update

    @NotNull(message = "Quantity cannot be null")
    @PositiveOrZero(message = "Quantity must be a non-negative integer")
    private Integer quantity; // The new quantity

    // Getters and Setters
    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
} 