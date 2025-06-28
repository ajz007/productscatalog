package com.scaler.capstone.productcatalog.product.dao.mysql.dao;

import com.scaler.capstone.productcatalog.product.dao.IUpdateProductsDao;
import com.scaler.capstone.productcatalog.product.dao.mysql.repo.CategoryRepository;
import com.scaler.capstone.productcatalog.product.dao.mysql.ProductEntity;
import com.scaler.capstone.productcatalog.product.dao.mysql.ProductEntityMapper;
import com.scaler.capstone.productcatalog.product.dao.mysql.repo.ProductRepository;
import com.scaler.capstone.productcatalog.product.model.Category;
import com.scaler.capstone.productcatalog.product.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("mysqlUpdateProductDao")
public class UpdateProductsMySqlDao implements IUpdateProductsDao {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UpdateProductsMySqlDao(ProductRepository repository, CategoryRepository categoryRepository, EntityManager entityManager) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.entityManager = entityManager;
    }

    @Override
    public void update(Product product) {
            var entity = ProductEntityMapper.fromProduct(product);
            repository.save(entity);
    }

    @Override
    public Product create(Product product) {
        var categories = categoryRepository.getCategoryEntitiesByName(product.getCategory().name());
        var entity = new ProductEntity(null, product.getTitle(), product.getPrice(), categories.get(0), product.getDescription(), product.getImage());
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
