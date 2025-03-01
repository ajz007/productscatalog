package com.scaler.capstone.productcatalog.product.model.dao;

import com.scaler.capstone.productcatalog.product.model.Product;

import java.util.List;

public interface IGetProductsDao {

    /**
     * Get all products.
     *
     * @return List of {@link Product}
     */
    List<Product> getProducts();

    /**
     * Get specific product based on id.
     *
     * @param id id
     * @return List of @{@link Product}
     */
    Product getProduct(String id);
}
