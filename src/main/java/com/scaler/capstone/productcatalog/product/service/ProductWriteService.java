package com.scaler.capstone.productcatalog.product.service;

import com.scaler.capstone.productcatalog.product.model.Product;
import com.scaler.capstone.productcatalog.product.dao.IUpdateProductsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProductWriteService {

    private IUpdateProductsDao updateProductsDao;

    @Autowired
    public ProductWriteService(@Qualifier("mysqlUpdateProductDao")IUpdateProductsDao updateProductsDao) {
        this.updateProductsDao = updateProductsDao;
    }

    public Product update(Product product) {
        updateProductsDao.update(product);
        return product;
    }

    public Product create(Product product) {
        return updateProductsDao.create(product);
    }
}
