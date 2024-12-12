package com.dn.shop.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders") // "order" is a reserved keyword in SQL
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItems;

    private BigDecimal totalPrice;
    private String status; // e.g., "PENDING", "COMPLETED", etc.
    private LocalDateTime createdAt;

    // Method to calculate the total price of the order
    public BigDecimal calculateTotalPrice() {
        BigDecimal total = BigDecimal.ZERO; // Initialize total price

        for (OrderItem item : orderItems) {
            // Calculate total price for each item and add to total
            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        this.totalPrice = total; // Update the totalPrice field
        return total; // Return the calculated total price
    }
} 