package com.scaler.capstone.productcatalog.product.model;

import java.util.Arrays;

public enum Category {
    ELECTRONICS, JEWELLERY, MENS_CLOTHING, WOMEN_CLOTHING, UNKNOWN;

    public static Category fromString(String value) {
        return Arrays.stream(Category.values())
                .filter(e -> e.name().equalsIgnoreCase(value.replace(" ", "_").replace("'", "")))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
