package com.dn.shop.model.dto.cart;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import com.dn.shop.model.dto.order.CartItemDTO;
import com.dn.shop.model.entity.Product;

public class CartDTO {
    private Long id; // Unique identifier of the cart
    private Long userId; // The ID of the user owning the cart
    private List<CartItemDTO> cartItems; // A list of CartItemDTO objects
    private BigDecimal totalPrice; // The total price of the items in the cart
    private List<Product> cart; // Assuming Product is the type of items in the cart

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
        this.cartItems = cartItems;
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
        return cart != null ? cart : new ArrayList<>(); // Return an empty list if cart is null
    }
} 