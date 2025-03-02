package com.scaler.capstone.productcatalog.controller;

import com.scaler.capstone.productcatalog.product.model.Category;
import com.scaler.capstone.productcatalog.product.model.Product;
import com.scaler.capstone.productcatalog.product.service.ProductReadService;
import com.scaler.capstone.productcatalog.product.service.ProductWriteService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController("/")
public class ProductController {

    private final ProductReadService productReadService;
    private final ProductWriteService productWriteService;

    public ProductController(ProductReadService productReadService, ProductWriteService productWriteService) {
        this.productReadService = productReadService;
        this.productWriteService = productWriteService;
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productReadService.getProducts();
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable int id) {
        return productReadService.getProducts(String.valueOf(id));
    }

    @GetMapping("/products/categories")
    public List<Product> getProductCategories() {
        return List.of(Product.builder()
                .withId(1)
                .withPrice(BigDecimal.ONE)
                .withImage("image")
                .withCategory(Category.JEWELLERY)
                .withDescription("description")
                .withTitle("title")
                .build());
    }

    @GetMapping("/products/categories/{category}")
    public List<Product> getProductCategories(@PathVariable Category category) {
        return List.of(Product.builder()
                .withId(1)
                .withPrice(BigDecimal.ONE)
                .withImage("image")
                .withCategory(Category.JEWELLERY)
                .withDescription("description")
                .withTitle("title")
                .build());
    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product product) {
        productWriteService.create(product);
        return product;
    }

    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable int id, @RequestBody Product product) {
        product.setId(id);
        return productWriteService.update(product);
    }

    @PatchMapping("/products/{id}")
    public Product patchProduct(@PathVariable int id, @RequestBody Product product) {
        return product;
    }

    @DeleteMapping("/products/{id}")
    public Product deleteProduct(@PathVariable int id) {
        return Product.builder()
                .withId(1)
                .withPrice(BigDecimal.ONE)
                .withImage("image")
                .withCategory(Category.JEWELLERY)
                .withDescription("description")
                .withTitle("title")
                .build();
    }
}
