package com.scaler.capstone.productcatalog.product.dao.mysql;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.JoinType;
import java.util.Locale;

public final class ProductSpecifications {

    private ProductSpecifications() {
    }

    public static Specification<ProductEntity> hasSearchText(String searchText) {
        if (searchText == null || searchText.isBlank()) {
            return null;
        }

        String normalized = "%" + searchText.trim().toLowerCase(Locale.ROOT) + "%";
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), normalized),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), normalized)
        );
    }

    public static Specification<ProductEntity> hasCategory(String categoryName) {
        if (categoryName == null || categoryName.isBlank()) {
            return null;
        }

        String normalized = categoryName.trim().toLowerCase(Locale.ROOT);
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                criteriaBuilder.lower(root.join("category", JoinType.INNER).get("name")),
                normalized
        );
    }
}
