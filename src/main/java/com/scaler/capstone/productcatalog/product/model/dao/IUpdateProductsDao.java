package com.scaler.capstone.productcatalog.product.model.dao;

import com.scaler.capstone.productcatalog.product.model.Product;

public interface IUpdateProductsDao {

    /**
     * Update the product.
     * @param product {@link Product}
     */
    void update(Product product);
}
