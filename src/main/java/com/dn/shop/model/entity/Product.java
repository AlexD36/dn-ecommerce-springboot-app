package com.dn.shop.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String description;

    // Assuming you have a stock field to manage inventory
    private int stock;

    // Reference to Category
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Method to reduce stock
    public void reduceStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        if (quantity > this.stock) {
            throw new IllegalArgumentException("Insufficient stock available.");
        }
        this.stock -= quantity; // Reduce stock by the specified quantity
    }

    // Helper method to display the category name
    public String getCategoryName() {
        return category != null ? category.getName() : "No category assigned";
    }
}
