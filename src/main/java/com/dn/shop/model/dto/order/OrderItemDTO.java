package com.dn.shop.model.dto.order;

import java.math.BigDecimal;

public class OrderItemDTO {
    private Long id; // Unique identifier of the order item
    private Long productId; // The ID of the product in this order item
    private String productName; // The name of the product
    private int quantity; // The quantity ordered
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