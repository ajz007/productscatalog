package com.scaler.capstone.productcatalog.product.dao.fakestore;

import com.scaler.capstone.productcatalog.product.model.Product;
import com.scaler.capstone.productcatalog.product.dao.IGetProductsDao;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * DAO class to access the GetProducts Service exposed by FakeStore Dao.
 */
@Component
public class GetProductsFakeStoreDao implements IGetProductsDao {

    private final RestTemplateBuilder restTemplateBuilder;

    public GetProductsFakeStoreDao(RestTemplateBuilder builder) {
        this.restTemplateBuilder = builder;
    }

    @Override
    public List<Product> getProducts() {

        var restTemplate = restTemplateBuilder.build();
        /**
         * Only gets response body
         */
//        var response = restTemplate.getForObject("https://fakestoreapi.com/products",
//                FakeStoreProductDTO[].class);

        /**
         * Get response body along with response status headers etc
         */
//        var response = restTemplate.getForEntity("https://fakestoreapi.com/products",
//                FakeStoreProductDTO[].class);

        /**
         * We can pass both request type, htto headers and can validate the http response.
         * More control over request and response.
         */

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        var response = restTemplate.exchange("https://fakestoreapi.com/products",
                HttpMethod.GET,
                entity,
                FakeStoreProductDTO[].class
                );

        return FakeStoreMapper.mapToProductList(response.getBody());
    }

    @Override
    public Product getProduct(String id) {
        var restTemplate = restTemplateBuilder.build();
        var response = restTemplate.getForEntity("https://fakestoreapi.com/products/" + id, FakeStoreProductDTO.class);
        return FakeStoreMapper.mapToProduct(response.getBody());
    }
}
