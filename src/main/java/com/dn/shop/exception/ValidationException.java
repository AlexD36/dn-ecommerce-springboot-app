package com.dn.shop.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
} 