package com.scaler.capstone.productcatalog.controller;

import com.scaler.capstone.productcatalog.product.model.Product;
import com.scaler.capstone.productcatalog.product.service.ProductReadService;
import com.scaler.capstone.productcatalog.product.service.ProductWriteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/")
public class SearchController {

    private final SearchProductsService searchService;

    public SearchController(SearchProductsService searchService) {
        this.searchService = productReadService;
    }


    @PostMapping("/search")
    public List<Product> searchProducts() {

    }

}
