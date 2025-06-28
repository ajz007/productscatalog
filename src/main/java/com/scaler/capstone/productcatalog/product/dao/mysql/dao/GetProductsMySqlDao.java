package com.scaler.capstone.productcatalog.product.dao.mysql.dao;

import com.scaler.capstone.productcatalog.product.dao.IGetProductsDao;
import com.scaler.capstone.productcatalog.product.dao.mysql.ProductEntity;
import com.scaler.capstone.productcatalog.product.dao.mysql.ProductEntityMapper;
import com.scaler.capstone.productcatalog.product.dao.mysql.repo.ProductRepository;
import com.scaler.capstone.productcatalog.product.model.Product;
import com.scaler.capstone.productcatalog.product.service.ProductNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("getProductsMySqlDao")
public class GetProductsMySqlDao implements IGetProductsDao {

    ProductRepository productRepository;

    public GetProductsMySqlDao(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProducts() {
        var prodList =  productRepository.findAll();
        var productList = new ArrayList<Product>();
        for (ProductEntity productEntity : prodList) {
            productList.add(ProductEntityMapper.toProduct(productEntity));
        }
        return productList;
    }

    @Override
    public Product getProduct(String id) {

            var entity = productRepository
                    .findById(Integer.valueOf(id));

        return  ProductEntityMapper
                .toProduct(entity.orElseThrow(() -> new ProductNotFoundException(String.format("No Product exists with id: %s", id))));
    }
}
