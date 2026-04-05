package com.scaler.capstone.productcatalog.product.service;

public class InvalidProductException extends RuntimeException {

    public InvalidProductException(String message) {
        super(message);
    }
}
