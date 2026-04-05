package com.scaler.capstone.productcatalog.product.dao;

import com.scaler.capstone.productcatalog.product.model.Product;

import java.util.List;

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
    Product update(Product product);

    /**
     *
     * @param id
     */
    void delete(int id);
}
