package com.scaler.capstone.productcatalog.product.dao.mysql.repo;

import com.scaler.capstone.productcatalog.product.dao.mysql.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
}
