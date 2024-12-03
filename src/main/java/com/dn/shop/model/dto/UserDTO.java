package com.dn.shop.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.dn.shop.model.entity.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {

    private Long id;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private List<Product> productResources;
}
