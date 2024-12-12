package com.dn.shop.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class LoginDTO {
    
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email; // The user's email address

    @NotNull(message = "Password cannot be null")
    private String password; // The user's password

    // Getters and Setters
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