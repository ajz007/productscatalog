package com.scaler.capstone.productcatalog.product.dao.mysql.repo;

import com.scaler.capstone.productcatalog.product.dao.mysql.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    List<CategoryEntity> getCategoryEntitiesByName(String name);
}
