package com.scaler.capstone.productcatalog.cart.model;

import com.scaler.capstone.productcatalog.product.model.Product;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class Cart {

    private Integer id;
    private String userId;
    private LocalDate date;
    private List<Product> products;
}
