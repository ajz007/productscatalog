package com.scaler.capstone.productcatalog.controller;

import com.scaler.capstone.productcatalog.product.dto.PagedResponse;
import com.scaler.capstone.productcatalog.product.dto.ProductResponse;
import com.scaler.capstone.productcatalog.product.model.Product;
import com.scaler.capstone.productcatalog.product.service.ProductReadService;
import com.scaler.capstone.productcatalog.product.service.ProductWriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductReadService productReadService;
    private final ProductWriteService productWriteService;

    public ProductController(ProductReadService productReadService, ProductWriteService productWriteService) {
        this.productReadService = productReadService;
        this.productWriteService = productWriteService;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<ProductResponse>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String category
    ) {
        return ResponseEntity.ok(
                productReadService.getProducts(page, size, sortBy, direction, search, category)
        );
    }

    @GetMapping("/categories")
    public List<String> getCategories() {
        return productReadService.getCategories();
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable int id) {
        return productReadService.getProduct(id);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productWriteService.create(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable int id, @RequestBody Product product) {
        product.setId(id);
        return productWriteService.update(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable int id) {
        productWriteService.delete(id);
    }
}
