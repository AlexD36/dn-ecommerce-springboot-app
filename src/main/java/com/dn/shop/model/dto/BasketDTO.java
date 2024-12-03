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
public class BasketDTO {
    @NotNull
    private List<Product> basket;
}
