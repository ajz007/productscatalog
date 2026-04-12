package com.scaler.capstone.productcatalog.product.service;

import com.scaler.capstone.productcatalog.product.dao.mysql.CategoryEntity;
import com.scaler.capstone.productcatalog.product.dao.mysql.ProductEntity;
import com.scaler.capstone.productcatalog.product.dao.mysql.repo.CategoryRepository;
import com.scaler.capstone.productcatalog.product.dao.mysql.repo.ProductRepository;
import com.scaler.capstone.productcatalog.product.dto.PagedResponse;
import com.scaler.capstone.productcatalog.product.dto.ProductResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductReadServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductReadService productReadService;

    @Test
    void getProductsReturnsPagedResponseForHappyPath() {
        ProductEntity productEntity = productEntity(1, "Phone", "electronics");
        Page<ProductEntity> page = new PageImpl<>(List.of(productEntity));
        when(productRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        PagedResponse<ProductResponse> response = productReadService.getProducts(0, 10, "title", "asc", "phone", "electronics");

        assertThat(response.getContent()).hasSize(1);
        assertThat(response.getContent().get(0).getTitle()).isEqualTo("Phone");
        assertThat(response.getContent().get(0).getCategory()).isEqualTo("electronics");
        assertThat(response.getPage()).isEqualTo(0);
        assertThat(response.getSize()).isEqualTo(1);
        assertThat(response.isHasNext()).isFalse();
        assertThat(response.isHasPrevious()).isFalse();

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(productRepository).findAll(any(Specification.class), pageableCaptor.capture());
        Pageable pageable = pageableCaptor.getValue();
        assertThat(pageable.getPageNumber()).isEqualTo(0);
        assertThat(pageable.getPageSize()).isEqualTo(10);
        assertThat(pageable.getSort().getOrderFor("title")).isEqualTo(new Sort.Order(Sort.Direction.ASC, "title"));
    }

    @Test
    void getProductsRejectsNegativePage() {
        assertThatThrownBy(() -> productReadService.getProducts(-1, 10, "id", "asc", null, null))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("Page must be greater than or equal to 0");
    }

    @Test
    void getProductsRejectsInvalidSize() {
        assertThatThrownBy(() -> productReadService.getProducts(0, 0, "id", "asc", null, null))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("Size must be greater than 0");
    }

    @Test
    void getProductsRejectsInvalidSortField() {
        assertThatThrownBy(() -> productReadService.getProducts(0, 10, "unknownField", "asc", null, null))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("Invalid sort field: unknownField");
    }

    @Test
    void getProductsRejectsInvalidSortDirection() {
        assertThatThrownBy(() -> productReadService.getProducts(0, 10, "id", "sideways", null, null))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("Invalid sort direction: sideways");
    }

    @Test
    void getProductsRejectsInvalidCategory() {
        assertThatThrownBy(() -> productReadService.getProducts(0, 10, "id", "asc", null, "toys"))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("Invalid category: toys");
    }

    @Test
    void getProductReturnsMappedProductResponse() {
        when(productRepository.findById(5)).thenReturn(Optional.of(productEntity(5, "Watch", "jewellery")));

        ProductResponse response = productReadService.getProduct(5);

        assertThat(response.getId()).isEqualTo(5);
        assertThat(response.getTitle()).isEqualTo("Watch");
        assertThat(response.getCategory()).isEqualTo("jewellery");
    }

    @Test
    void getProductThrowsWhenMissing() {
        when(productRepository.findById(5)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productReadService.getProduct(5))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("No product exists with id: 5");
    }

    @Test
    void getCategoriesReturnsSortedCategoryNames() {
        when(categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name"))).thenReturn(List.of(
                categoryEntity("electronics"),
                categoryEntity("jewellery")
        ));

        List<String> categories = productReadService.getCategories();

        assertThat(categories).containsExactly("electronics", "jewellery");
    }

    private ProductEntity productEntity(int id, String title, String categoryName) {
        ProductEntity entity = new ProductEntity();
        entity.setId(id);
        entity.setTitle(title);
        entity.setDescription(title + " description");
        entity.setPrice(BigDecimal.valueOf(999));
        entity.setImage("image-url");
        entity.setStockQuantity(10);
        entity.setCategory(categoryEntity(categoryName));
        return entity;
    }

    private CategoryEntity categoryEntity(String name) {
        CategoryEntity entity = new CategoryEntity();
        entity.setId(1L);
        entity.setName(name);
        return entity;
    }
}
