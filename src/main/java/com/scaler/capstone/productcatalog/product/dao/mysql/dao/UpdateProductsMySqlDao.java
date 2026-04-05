package com.scaler.capstone.productcatalog.product.dao.mysql.dao;

import com.scaler.capstone.productcatalog.product.dao.IUpdateProductsDao;
import com.scaler.capstone.productcatalog.product.dao.mysql.CategoryEntity;
import com.scaler.capstone.productcatalog.product.dao.mysql.ProductEntity;
import com.scaler.capstone.productcatalog.product.dao.mysql.ProductEntityMapper;
import com.scaler.capstone.productcatalog.product.dao.mysql.repo.CategoryRepository;
import com.scaler.capstone.productcatalog.product.dao.mysql.repo.ProductRepository;
import com.scaler.capstone.productcatalog.product.model.Product;
import com.scaler.capstone.productcatalog.product.service.InvalidProductException;
import com.scaler.capstone.productcatalog.product.service.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("mysqlUpdateProductDao")
public class UpdateProductsMySqlDao implements IUpdateProductsDao {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public UpdateProductsMySqlDao(ProductRepository repository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product update(Product product) {
        ProductEntity existingEntity = repository.findById(product.getId())
                .orElseThrow(() -> new ProductNotFoundException(
                        String.format("No product exists with id: %s", product.getId())));

        existingEntity.setTitle(product.getTitle());
        existingEntity.setPrice(product.getPrice());
        existingEntity.setCategory(resolveCategory(product));
        existingEntity.setDescription(product.getDescription());
        existingEntity.setImage(product.getImage());

        return ProductEntityMapper.toProduct(repository.save(existingEntity));
    }

    @Override
    public void delete(int id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException(String.format("No product exists with id: %s", id));
        }
        repository.deleteById(id);
    }

    @Override
    public Product create(Product product) {
        ProductEntity entity = new ProductEntity(
                null,
                product.getTitle(),
                product.getPrice(),
                resolveCategory(product),
                product.getDescription(),
                product.getImage()
        );
        return ProductEntityMapper.toProduct(repository.save(entity));
    }

    private CategoryEntity resolveCategory(Product product) {
        String categoryName = product.getCategory().name();
        return categoryRepository.findByNameIgnoreCase(categoryName)
                .orElseThrow(() -> new InvalidProductException(
                        String.format("Category does not exist: %s", categoryName)));
    }
}
