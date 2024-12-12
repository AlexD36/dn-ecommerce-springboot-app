package com.dn.shop.model.dto.user;

import com.dn.shop.model.dto.cart.CartDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterUserDTO {
    
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String name; // The user's name

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email; // The user's email address

    @NotNull(message = "Password cannot be null")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password; // The user's password

    private String confirmPassword; // Optional, for password confirmation

    private String firstName;
    private String lastName;
    private CartDTO cartDTO; // Assuming this is also part of the DTO

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public CartDTO getCartDTO() {
        return cartDTO;
    }

    /**
     * Adds a user after validating the password and confirm password.
     * @throws IllegalArgumentException if passwords do not match.
     */
    public void addUser() {
        // Validate that the password and confirm password match
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match.");
        }
        
        // Logic to add the user (e.g., save to database) would go here
        // For now, we will just print a success message
        System.out.println("User " + name + " added successfully with email " + email + ".");
    }
} 