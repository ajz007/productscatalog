package com.scaler.capstone.productcatalog.product.dao.fakestore;

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
