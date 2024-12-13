package com.dn.shop.model.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import com.dn.shop.model.entity.OrderStatus;

@Builder
public class OrderDTO {
    private Long id; // Unique identifier of the order
    private Long userId; // The ID of the user who placed the order
    private List<OrderItemDTO> orderItems; // A list of OrderItemDTO objects
    private BigDecimal totalPrice; // Total price of the order
    private OrderStatus status; // The current status of the order (e.g., PENDING, SHIPPED, DELIVERED)
    private LocalDateTime orderDate; // Date when the order was placed
    private LocalDateTime createdAt;  // Add this field

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
} 