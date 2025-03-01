package com.scaler.capstone.productcatalog.fakestore.dto;

import com.scaler.capstone.productcatalog.product.model.Category;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
public class FakeStoreProductDTO {
    private Integer id;
    private String title;
    private BigDecimal price;
    private String category;
    private String description;
    private String image;


}
