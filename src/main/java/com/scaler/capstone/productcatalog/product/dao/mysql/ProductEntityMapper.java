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

    /**
     * Convert to Product Entity.
     *
     * @param product
     * @return @{@link ProductEntity}
     */
    public static ProductEntity fromProduct(Product product) {
        return ProductEntity.builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .category(CategoryEntity.builder()
                        .withName(product.getCategory().name())
                        .build())
                .description(product.getDescription())
                .image(product.getImage())
                .build();
    }
}
