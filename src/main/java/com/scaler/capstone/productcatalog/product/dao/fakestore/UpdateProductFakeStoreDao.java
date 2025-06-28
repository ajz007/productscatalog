package com.scaler.capstone.productcatalog.product.dao.fakestore;

import com.scaler.capstone.productcatalog.product.model.Product;
import com.scaler.capstone.productcatalog.product.dao.IUpdateProductsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component("fakestoreUpdateProductDao")
public class UpdateProductFakeStoreDao implements IUpdateProductsDao {

    private final RestTemplateBuilder templateBuilder;

    @Autowired
    public UpdateProductFakeStoreDao(RestTemplateBuilder templateBuilder) {
        this.templateBuilder = templateBuilder;
    }

    @Override
    public Product create(Product product) {
        //Not implemented
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Product product) {
        var template = templateBuilder.build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<FakeStoreProductDTO> request = new HttpEntity<>(FakeStoreMapper.mapToFakeStoreDTO(product), headers);

         template.exchange("https://fakestoreapi.com/products/" + product.getId(), HttpMethod.PUT,
                    request, FakeStoreProductDTO.class);
    }
}
