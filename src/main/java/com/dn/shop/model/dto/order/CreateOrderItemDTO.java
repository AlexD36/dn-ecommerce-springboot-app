package com.dn.shop.model.dto.order;

import jakarta.validation.constraints.NotNull;

public class CreateOrderItemDTO {
    
    @NotNull(message = "Product ID cannot be null")
    private Long productId; // The ID of the product to order

    @NotNull(message = "Quantity cannot be null")
    private Integer quantity; // The quantity to order

    // Getters and Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
} 