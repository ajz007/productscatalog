package com.scaler.capstone.productcatalog.product.model.service;

import com.scaler.capstone.productcatalog.product.model.Product;
import com.scaler.capstone.productcatalog.product.model.dao.IGetProductsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * A common service class for all read operations to Fake Store GEt calls.
 */
@Service
public class ProductReadService {

    private final IGetProductsDao getProductsDao;

    @Autowired
    public ProductReadService(IGetProductsDao getProductsDao) {
        this.getProductsDao = getProductsDao;
    }

    public List<Product> getProducts() {
        return getProductsDao.getProducts();
    }

    public Product getProducts(String id) {
        return getProductsDao.getProduct(id);
    }
}
