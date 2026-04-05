package com.scaler.capstone.productcatalog.product.dao;

import com.scaler.capstone.productcatalog.product.model.Product;

import java.util.List;

public interface ISearchDao {

    List<Product> search();
}
