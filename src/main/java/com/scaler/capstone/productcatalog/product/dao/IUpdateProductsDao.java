package com.scaler.capstone.productcatalog.product.dao;

import com.scaler.capstone.productcatalog.product.model.Product;

public interface IUpdateProductsDao {

    /**
     * Create the product.
     * @param product {@link Product}
     */
    Product create(Product product);

    /**
     * Update the product.
     * @param product {@link Product}
     */
    void update(Product product);
}
