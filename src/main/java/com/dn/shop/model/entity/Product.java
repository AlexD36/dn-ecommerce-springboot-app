package com.dn.shop.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_table")
public class Product extends BaseEntity {
    
    @Column(nullable = false)
    private String name; // Product name

    @Column(nullable = false)
    private String description; // Detailed product description

    @Column(nullable = false)
    private BigDecimal price; // Product price

    @Column(nullable = false)
    private int stock; // Quantity available in inventory

    @ManyToOne // Many-to-One relationship with Category
    @JoinColumn(name = "category_id", nullable = false) // Foreign key column
    private Category category;

    private LocalDateTime createdAt; // Timestamp of product creation
    private LocalDateTime updatedAt; // Timestamp of the last update

    public Product(String name, String description, BigDecimal price, int stock, Category category) {
        this.name = name;
        this.description = description;
        setPrice(price); // Use setter for validation
        setStock(stock); // Use setter for validation
        this.category = category; // Set the category
    }
}
