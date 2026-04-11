package com.scaler.capstone.productcatalog.product.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true, setterPrefix = "with")
@Schema(description = "Product payload")
public class Product {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;
    @Schema(example = "Apple iPhone 15")
    private String title;
    @Schema(example = "79999.00")
    private BigDecimal price;
    private Category category;
    @Schema(example = "Latest generation iPhone with A16 chip")
    private String description;
    @Schema(example = "https://example.com/images/iphone15.jpg")
    private String image;
    private Integer stockQuantity;

}
