package com.scaler.capstone.productcatalog.product.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true, setterPrefix = "with")
public class Product {

    private Integer id;
    private String title;
    private BigDecimal price;
    private Category category;
    private String description;
    private String image;

}
