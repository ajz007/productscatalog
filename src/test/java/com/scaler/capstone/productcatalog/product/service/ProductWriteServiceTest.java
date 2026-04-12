package com.scaler.capstone.productcatalog.product.service;

import com.scaler.capstone.productcatalog.product.dao.IUpdateProductsDao;
import com.scaler.capstone.productcatalog.product.model.Category;
import com.scaler.capstone.productcatalog.product.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductWriteServiceTest {

    @Mock
    private IUpdateProductsDao updateProductsDao;

    @InjectMocks
    private ProductWriteService productWriteService;

    @Test
    void createPersistsValidProduct() {
        Product product = validProduct(null);
        Product created = validProduct(1);
        when(updateProductsDao.create(product)).thenReturn(created);

        Product response = productWriteService.create(product);

        assertThat(response.getId()).isEqualTo(1);
        verify(updateProductsDao).create(product);
    }

    @Test
    void createRejectsInvalidCategory() {
        Product product = validProduct(null);
        product.setCategory(Category.UNKNOWN);

        assertThatThrownBy(() -> productWriteService.create(product))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("Product category is invalid");
    }

    @Test
    void updatePersistsValidProduct() {
        Product product = validProduct(1);
        when(updateProductsDao.update(product)).thenReturn(product);

        Product response = productWriteService.update(product);

        assertThat(response.getId()).isEqualTo(1);
        verify(updateProductsDao).update(product);
    }

    @Test
    void updateRejectsMissingId() {
        Product product = validProduct(null);

        assertThatThrownBy(() -> productWriteService.update(product))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("Product id is required");
    }

    @Test
    void updateRejectsMissingTitle() {
        Product product = validProduct(1);
        product.setTitle("  ");

        assertThatThrownBy(() -> productWriteService.update(product))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("Product title is required");
    }

    @Test
    void deleteDelegatesToDao() {
        productWriteService.delete(9);

        verify(updateProductsDao).delete(9);
    }

    private Product validProduct(Integer id) {
        return Product.builder()
                .withId(id)
                .withTitle("Phone")
                .withPrice(BigDecimal.valueOf(1999))
                .withCategory(Category.ELECTRONICS)
                .withDescription("Flagship phone")
                .withImage("image-url")
                .withStockQuantity(10)
                .build();
    }
}
