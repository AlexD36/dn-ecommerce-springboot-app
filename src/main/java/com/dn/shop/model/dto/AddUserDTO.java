package com.dn.shop.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.dn.shop.model.entity.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserDTO {
    @NotNull
    private String first_name;
    @NotNull
    private String last_name;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private BasketDTO basketDTO;
}
