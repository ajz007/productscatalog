package com.scaler.capstone.productcatalog.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Product Catalog API",
                version = "1.0",
                description = "Catalog APIs for product listing, details, and catalog administration."
        )
)
public class OpenApiConfig {

    @Bean
    public OpenAPI productCatalogOpenApi() {
        return new OpenAPI();
    }
}
