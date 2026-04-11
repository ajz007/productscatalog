package com.scaler.capstone.productcatalog.controller;

import com.scaler.capstone.productcatalog.product.dto.PagedResponse;
import com.scaler.capstone.productcatalog.product.dto.ProductResponse;
import com.scaler.capstone.productcatalog.product.model.Product;
import com.scaler.capstone.productcatalog.product.service.ProductReadService;
import com.scaler.capstone.productcatalog.product.service.ProductWriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Products", description = "Product catalog query and maintenance endpoints")
public class ProductController {

    private final ProductReadService productReadService;
    private final ProductWriteService productWriteService;

    public ProductController(ProductReadService productReadService, ProductWriteService productWriteService) {
        this.productReadService = productReadService;
        this.productWriteService = productWriteService;
    }

    @GetMapping
    @Operation(
            summary = "List products",
            description = "Returns paginated products with optional sorting, search text, and category filtering."
    )
    public ResponseEntity<PagedResponse<ProductResponse>> getProducts(
            @Parameter(description = "Zero-based page number", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sortable field", example = "id")
            @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction", example = "asc")
            @RequestParam(defaultValue = "asc") String direction,
            @Parameter(description = "Search text across title and description", example = "iphone")
            @RequestParam(required = false) String search,
            @Parameter(description = "Category filter", example = "electronics")
            @RequestParam(required = false) String category
    ) {
        return ResponseEntity.ok(
                productReadService.getProducts(page, size, sortBy, direction, search, category)
        );
    }

    @GetMapping("/categories")
    @Operation(summary = "List categories", description = "Returns all product categories available in the database.")
    public List<String> getCategories() {
        return productReadService.getCategories();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product details", description = "Returns a single product by id.")
    public ProductResponse getProduct(@PathVariable int id) {
        return productReadService.getProduct(id);
    }

    @PostMapping
    @Operation(summary = "Create a product", description = "Creates a new product in the catalog.")
    public Product addProduct(@RequestBody Product product) {
        return productWriteService.create(product);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product", description = "Updates an existing product by id.")
    public Product updateProduct(@PathVariable int id, @RequestBody Product product) {
        product.setId(id);
        return productWriteService.update(product);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product", description = "Deletes a product by id.")
    public void deleteProduct(@PathVariable int id) {
        productWriteService.delete(id);
    }
}
