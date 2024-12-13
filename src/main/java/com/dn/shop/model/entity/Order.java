package com.dn.shop.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders") // "order" is a reserved keyword in SQL
public class Order extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItems;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItem;

    private BigDecimal totalPrice;
    private String status; // e.g., "PENDING", "COMPLETED", etc.
    private LocalDateTime createdAt;

    // Method to calculate the total price of the order
    public void calculateTotalPrice() {
        BigDecimal total = BigDecimal.ZERO; // Initialize total price
    
        for (OrderItem item : orderItems) {
            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
    
        this.totalPrice = total; // Update totalPrice field
    }

    public void setStatus(String status) {
        // logic
        this.status = status;
    }

    @PrePersist
    public void prePersist() {
    if (createdAt == null) {
        createdAt = LocalDateTime.now();
    }
}
} 