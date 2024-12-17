package com.dn.shop.exception;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> ServiceResponse<T> success(T data) {
        return new ServiceResponse<>(true, "Operation successful", data);
    }

    public static <T> ServiceResponse<T> success(String message, T data) {
        return new ServiceResponse<>(true, message, data);
    }

    public static ServiceResponse<Void> error(String message) {
        return new ServiceResponse<>(false, message, null);
    }
} 