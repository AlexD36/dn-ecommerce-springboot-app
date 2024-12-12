package com.dn.shop.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UpdateUserDTO {
    
    @Size(min = 2, message = "Name must be at least 2 characters long")
    private String name; // Updated name (optional)

    @Email(message = "Email should be valid")
    private String email; // Updated email address (optional)

    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password; // New password (optional)

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
} 