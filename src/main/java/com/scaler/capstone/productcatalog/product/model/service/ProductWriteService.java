package com.scaler.capstone.productcatalog.product.model.service;

import com.scaler.capstone.productcatalog.product.model.Product;
import com.scaler.capstone.productcatalog.product.model.dao.IUpdateProductsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductWriteService {

    private IUpdateProductsDao updateProductsDao;

    @Autowired
    public ProductWriteService(IUpdateProductsDao updateProductsDao) {
        this.updateProductsDao = updateProductsDao;
    }

    public Product update(Product product) {
        updateProductsDao.update(product);
        return product;
    }
}
