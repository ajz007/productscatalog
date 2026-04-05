package com.scaler.capstone.productcatalog.product.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class ProductResponse {
    Integer id;
    String title;
    String description;
    BigDecimal price;
    String imageUrl;
    String category;
    Integer stockQuantity;
}
