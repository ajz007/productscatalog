package com.scaler.capstone.productcatalog.product.dao.mysql;

import com.scaler.capstone.productcatalog.product.dao.IUpdateProductsDao;
import com.scaler.capstone.productcatalog.product.model.Category;
import com.scaler.capstone.productcatalog.product.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

//    @Override
//    public Product create(Product product) {
//        var entityManagerFactory = entityManager.getEntityManagerFactory();
//        var entityManager = entityManagerFactory.createEntityManager();
//        var transaction = entityManager.getTransaction();
//
//        transaction.begin();
//
//        // Fetch or create category manually (ensure it's attached)
//        var categories = entityManager.createQuery(
//                        "SELECT c FROM CategoryEntity c WHERE c.name = :name", CategoryEntity.class)
//                .setParameter("name", product.getCategory().name())
//                .getResultList();
//
//        CategoryEntity categoryEntity;
//        if (categories.isEmpty()) {
//            categoryEntity = CategoryEntity.builder()
//                    .withName(product.getCategory().name())
//                    .build();
//            entityManager.persist(categoryEntity);
//        } else {
//            categoryEntity = categories.get(0);
//        }
//
//        // Explicitly create product entity and set fields manually
//        ProductEntity productEntity = new ProductEntity();
//        productEntity.setTitle(product.getTitle());
//        productEntity.setPrice(product.getPrice());
//        productEntity.setCategory(categoryEntity);
//
//        // Persist explicitly
//        entityManager.persist(productEntity);
//
//        entityManager.flush(); // Force insert immediately
//        entityManager.refresh(productEntity); // Refresh after insert to ensure correctness
//
//        // Commit happens at the end of transaction automatically managed by Spring, but since you're manually controlling:
//        transaction.commit(); // explained below
//
//        return Product.builder()
//                .withId(productEntity.getId())
//                .withTitle(productEntity.getTitle())
//                .withPrice(productEntity.getPrice())
//                .withCategory(Category.fromString(categoryEntity.getName()))
//                .build();
//    }

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
