package com.scaler.capstone.productcatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.capstone.productcatalog.product.dto.PagedResponse;
import com.scaler.capstone.productcatalog.product.dto.ProductResponse;
import com.scaler.capstone.productcatalog.product.model.Category;
import com.scaler.capstone.productcatalog.product.model.Product;
import com.scaler.capstone.productcatalog.product.service.InvalidProductException;
import com.scaler.capstone.productcatalog.product.service.ProductNotFoundException;
import com.scaler.capstone.productcatalog.product.service.ProductReadService;
import com.scaler.capstone.productcatalog.product.service.ProductWriteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@Import(ExceptionControllerAdvice.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductReadService productReadService;

    @MockBean
    private ProductWriteService productWriteService;

    @Test
    void getProductsReturnsPagedResponse() throws Exception {
        PagedResponse<ProductResponse> response = PagedResponse.<ProductResponse>builder()
                .content(List.of(productResponse(1)))
                .page(0)
                .size(10)
                .totalElements(1)
                .totalPages(1)
                .hasNext(false)
                .hasPrevious(false)
                .build();
        when(productReadService.getProducts(0, 10, "id", "asc", null, null)).thenReturn(response);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].title").value("Phone"))
                .andExpect(jsonPath("$.page").value(0));
    }

    @Test
    void getProductReturnsProductResponse() throws Exception {
        when(productReadService.getProduct(1)).thenReturn(productResponse(1));

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Phone"));
    }

    @Test
    void getCategoriesReturnsList() throws Exception {
        when(productReadService.getCategories()).thenReturn(List.of("electronics", "jewellery"));

        mockMvc.perform(get("/products/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("electronics"))
                .andExpect(jsonPath("$[1]").value("jewellery"));
    }

    @Test
    void addProductReturnsCreatedPayload() throws Exception {
        Product product = product(1);
        when(productWriteService.create(org.mockito.ArgumentMatchers.any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product(null))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Phone"));
    }

    @Test
    void updateProductReturnsUpdatedPayload() throws Exception {
        Product updated = product(1);
        when(productWriteService.update(org.mockito.ArgumentMatchers.any(Product.class))).thenReturn(updated);

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product(null))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Phone"));
    }

    @Test
    void deleteProductReturnsOk() throws Exception {
        doNothing().when(productWriteService).delete(1);

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk());
    }

    @Test
    void getProductReturns404WhenMissing() throws Exception {
        when(productReadService.getProduct(99)).thenThrow(new ProductNotFoundException("No product exists with id: 99"));

        mockMvc.perform(get("/products/99"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No product exists with id: 99"));
    }

    @Test
    void getProductsReturns400ForInvalidInput() throws Exception {
        when(productReadService.getProducts(0, 10, "id", "asc", null, "toys"))
                .thenThrow(new InvalidProductException("Invalid category: toys"));

        mockMvc.perform(get("/products").param("category", "toys"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid category: toys"));
    }

    @Test
    void postReturns400ForInvalidProductPayload() throws Exception {
        doThrow(new InvalidProductException("Product title is required"))
                .when(productWriteService).create(org.mockito.ArgumentMatchers.any(Product.class));

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product(null))))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Product title is required"));
    }

    @Test
    void postReturns500ForUnexpectedFailure() throws Exception {
        doThrow(new RuntimeException("boom"))
                .when(productWriteService).create(org.mockito.ArgumentMatchers.any(Product.class));

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product(null))))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Internal server error"));
    }

    private ProductResponse productResponse(int id) {
        return ProductResponse.builder()
                .id(id)
                .title("Phone")
                .description("Flagship phone")
                .price(BigDecimal.valueOf(1999))
                .imageUrl("image-url")
                .category("electronics")
                .stockQuantity(10)
                .build();
    }

    private Product product(Integer id) {
        return Product.builder()
                .withId(id)
                .withTitle("Phone")
                .withDescription("Flagship phone")
                .withPrice(BigDecimal.valueOf(1999))
                .withImage("image-url")
                .withCategory(Category.ELECTRONICS)
                .withStockQuantity(10)
                .build();
    }
}
