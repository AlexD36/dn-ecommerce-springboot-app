package com.dn.shop.model.dto;

import java.math.BigDecimal;

public class CartItemDTO {
    private Long id; // Unique identifier of the cart item
    private Long productId; // The ID of the product
    private String productName; // The name of the product
    private int quantity; // The quantity of the product in the cart
    private BigDecimal price; // The price per unit of the product

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
} 