package com.scaler.capstone.productcatalog.controller;

import com.scaler.capstone.productcatalog.product.service.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleBadRequestException(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Exception exception) {
        exception.printStackTrace();
        return new ResponseEntity<>("The request failed with following exception."+exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
