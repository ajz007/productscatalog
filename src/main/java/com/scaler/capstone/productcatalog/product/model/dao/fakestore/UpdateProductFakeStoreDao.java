package com.scaler.capstone.productcatalog.product.model.dao.fakestore;

import com.scaler.capstone.productcatalog.fakestore.dto.FakeStoreProductDTO;
import com.scaler.capstone.productcatalog.product.model.Product;
import com.scaler.capstone.productcatalog.product.model.dao.IUpdateProductsDao;
import com.scaler.capstone.productcatalog.product.model.dao.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class UpdateProductFakeStoreDao implements IUpdateProductsDao {

    private RestTemplateBuilder templateBuilder;

    @Autowired
    public UpdateProductFakeStoreDao(RestTemplateBuilder templateBuilder) {
        this.templateBuilder = templateBuilder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Product product) {
        var template = templateBuilder.build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FakeStoreProductDTO> request = new HttpEntity<>(Mapper.mapToFakeStoreDTO(product), headers);

         template.exchange("https://fakestoreapi.com/products/" + product.getId(), HttpMethod.PUT,
                    request, FakeStoreProductDTO.class);
    }
}
