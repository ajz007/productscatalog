package com.scaler.capstone.productcatalog.product.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true, setterPrefix = "with")
public class Product {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;
    private String title;
    private BigDecimal price;
    private Category category;
    private String description;
    private String image;

}
