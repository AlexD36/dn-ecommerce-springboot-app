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
    
    private String name; // Product name
    private String description; // Detailed product description
    private BigDecimal price; // Product price
    private int stock; // Quantity available in inventory

    @ManyToOne // Many-to-One relationship with Category
    @JoinColumn(name = "category_id", nullable = false) // Foreign key column
    private Category category;

    private LocalDateTime createdAt; // Timestamp of product creation
    private LocalDateTime updatedAt; // Timestamp of the last update

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Method to reduce stock
    public void reduceStock(int quantity) {
        if (quantity <= stock) {
            this.stock -= quantity; // Reduce stock by the specified quantity
        } else {
            throw new IllegalArgumentException("Insufficient stock available.");
        }
    }

    // Add this constructor
    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
