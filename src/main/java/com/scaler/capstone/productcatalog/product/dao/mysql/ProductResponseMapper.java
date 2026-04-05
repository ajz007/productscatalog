package com.scaler.capstone.productcatalog.product.dao.mysql;

import com.scaler.capstone.productcatalog.product.dto.ProductResponse;

public class ProductResponseMapper {

    private ProductResponseMapper() {
    }

    public static ProductResponse toResponse(ProductEntity entity) {
        return ProductResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .imageUrl(entity.getImage())
                .category(entity.getCategory() == null ? null : entity.getCategory().getName())
                .stockQuantity(entity.getStockQuantity())
                .build();
    }
}
