package com.scaler.capstone.productcatalog.product.dao.mysql;

import com.scaler.capstone.productcatalog.product.model.Category;
import com.scaler.capstone.productcatalog.product.model.Product;

public class ProductEntityMapper {

    /**
     * Convert to Product Model.
     *
     * @return @{@link Product}
     */
    public static Product toProduct(ProductEntity entity) {
        return Product.builder()
                .withId(entity.getId())
                .withTitle(entity.getTitle())
                .withPrice(entity.getPrice())
                .withCategory(Category.fromString(entity.getCategory().getName()))
                .withDescription(entity.getDescription())
                .withImage(entity.getImage())
                .build();
    }
}
