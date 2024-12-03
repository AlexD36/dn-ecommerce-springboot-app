package com.dn.shop.model.dto;

import com.dn.shop.model.entity.Product;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("firstName")
    private String first_name;
    @JsonProperty("lastName")
    private String last_name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @JsonProperty("basket")
    private List<Product> productResources;
}
