package com.scaler.capstone.productcatalog.product.service;


public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(String message){
        super(message);
    }
}
