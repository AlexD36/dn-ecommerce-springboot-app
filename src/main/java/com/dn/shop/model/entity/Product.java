package com.dn.shop.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_table")
public class Product extends BaseEntity {
    
    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(nullable = false, length = 100)
    private String name; // Product name

    @NotBlank(message = "Product description is required")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Column(nullable = false, length = 1000)
    private String description; // Detailed product description

    @Positive(message = "Price must be greater than zero")
    @Column(nullable = false)
    private BigDecimal price; // Product price

    @Positive(message = "Stock must be greater than zero")
    @Column(nullable = false)
    private int stock; // Quantity available in inventory

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false) // Foreign key column
    private Category category;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // Timestamp of product creation

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt; // Timestamp of the last update

    public Product(String name, String description, BigDecimal price, int stock, Category category) {
        this.name = name;
        this.description = description;
        setPrice(price); // Use setter for validation
        setStock(stock); // Use setter for validation
        this.category = category; // Set the category
    }
}
