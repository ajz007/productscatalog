package com.scaler.capstone.productcatalog.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class ProductResponse {
    @Schema(example = "1")
    Integer id;
    String title;
    String description;
    @Schema(example = "79999.00")
    BigDecimal price;
    String imageUrl;
    String category;
    @Schema(example = "25")
    Integer stockQuantity;
}
