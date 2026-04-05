package com.scaler.capstone.productcatalog.product.service;

import com.scaler.capstone.productcatalog.product.dao.IUpdateProductsDao;
import com.scaler.capstone.productcatalog.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductWriteService {

    private final IUpdateProductsDao updateProductsDao;

    @Autowired
    public ProductWriteService(@Qualifier("mysqlUpdateProductDao") IUpdateProductsDao updateProductsDao) {
        this.updateProductsDao = updateProductsDao;
    }

    public Product update(Product product) {
        validate(product, true);
        return updateProductsDao.update(product);
    }

    @Transactional
    public Product create(Product product) {
        validate(product, false);
        return updateProductsDao.create(product);
    }

    public void delete(int id) {
        updateProductsDao.delete(id);
    }

    private void validate(Product product, boolean requireId) {
        if (product == null) {
            throw new InvalidProductException("Product payload is required");
        }
        if (requireId && product.getId() == null) {
            throw new InvalidProductException("Product id is required");
        }
        if (isBlank(product.getTitle())) {
            throw new InvalidProductException("Product title is required");
        }
        if (product.getPrice() == null) {
            throw new InvalidProductException("Product price is required");
        }
        if (product.getCategory() == null) {
            throw new InvalidProductException("Product category is required");
        }
        if (product.getCategory().isUnknown()) {
            throw new InvalidProductException("Product category is invalid");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
