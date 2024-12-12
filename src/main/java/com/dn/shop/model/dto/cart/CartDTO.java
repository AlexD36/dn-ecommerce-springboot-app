package com.dn.shop.model.dto.cart;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import com.dn.shop.model.dto.order.CartItemDTO;
import com.dn.shop.model.entity.Product;

public class CartDTO {
    private Long id; // Unique identifier of the cart
    private Long userId; // The ID of the user owning the cart
    private List<CartItemDTO> cartItems = new ArrayList<>(); // Initialize to avoid null
    private BigDecimal totalPrice; // The total price of the items in the cart
    private List<Product> cart = new ArrayList<>(); // Initialize to avoid null

    // Constructor
    public CartDTO() {
        // Initialize fields if necessary
    }

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

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = cartItems != null ? cartItems : new ArrayList<>(); // Avoid null
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Sets the list of products in the cart.
     * 
     * @param cart A list of Product objects to be set in the cart.
     * @throws IllegalArgumentException if the provided list is null.
     */
    public void setCart(List<Product> cart) {
        if (cart == null) {
            throw new IllegalArgumentException("Cart cannot be null.");
        }
        this.cart = cart;
    }

    public List<Product> getCart() {
        return cart; // Already initialized
    }
} 