package com.dn.shop.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class EmailUpdateDTO {
    @NotNull(message = "New email cannot be null")
    @Email(message = "New email must be valid")
    private String newEmail;

    // Getters and Setters
    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
} 