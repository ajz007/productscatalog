package com.scaler.capstone.productcatalog.product.dao.mysql;

import com.scaler.capstone.productcatalog.product.dao.IUpdateProductsDao;
import com.scaler.capstone.productcatalog.product.model.Category;
import com.scaler.capstone.productcatalog.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("mysqlUpdateProductDao")
public class UpdateProductsMySqlDao implements IUpdateProductsDao {

    private final ProductRepository repository;

    @Autowired
    public UpdateProductsMySqlDao(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void update(Product product) {
            var entity = ProductEntityMapper.fromProduct(product);
            repository.save(entity);
    }

    @Override
    public Product create(Product product) {
        product.setId(null);// Let database take care of this.
        var entity = ProductEntityMapper.fromProduct(product);
        var savedEntity = repository.save(entity);
        return Product.builder()
                .withId(savedEntity.getId())
                .withTitle(savedEntity.getTitle())
                .withPrice(savedEntity.getPrice())
                .withDescription(savedEntity.getDescription())
                .withImage(savedEntity.getImage())
                .withCategory(Category.fromString(savedEntity.getCategory().getName()))
                .build();
    }
}
