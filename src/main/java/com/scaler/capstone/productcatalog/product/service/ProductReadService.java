package com.scaler.capstone.productcatalog.product.service;

import com.scaler.capstone.productcatalog.product.dao.mysql.ProductEntity;
import com.scaler.capstone.productcatalog.product.dao.mysql.ProductResponseMapper;
import com.scaler.capstone.productcatalog.product.dao.mysql.ProductSpecifications;
import com.scaler.capstone.productcatalog.product.dao.mysql.repo.CategoryRepository;
import com.scaler.capstone.productcatalog.product.dao.mysql.repo.ProductRepository;
import com.scaler.capstone.productcatalog.product.dto.PagedResponse;
import com.scaler.capstone.productcatalog.product.dto.ProductResponse;
import com.scaler.capstone.productcatalog.product.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductReadService {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;
    private static final String DEFAULT_SORT_BY = "id";
    private static final String DEFAULT_DIRECTION = "asc";
    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "id",
            "title",
            "description",
            "price",
            "imageUrl",
            "category",
            "stockQuantity"
    );

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductReadService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public PagedResponse<ProductResponse> getProducts(
            Integer page,
            Integer size,
            String sortBy,
            String direction,
            String search,
            String category
    ) {
        int validatedPage = page == null ? DEFAULT_PAGE : page;
        int validatedSize = size == null ? DEFAULT_SIZE : size;
        validatePagination(validatedPage, validatedSize);

        Sort sort = buildSort(sortBy, direction);
        Pageable pageable = PageRequest.of(validatedPage, validatedSize, sort);
        Specification<ProductEntity> specification = Specification
                .where(ProductSpecifications.hasSearchText(search))
                .and(ProductSpecifications.hasCategory(normalizeCategory(category)));

        Page<ProductResponse> productPage = productRepository.findAll(specification, pageable)
                .map(ProductResponseMapper::toResponse);

        return PagedResponse.<ProductResponse>builder()
                .content(productPage.getContent())
                .page(productPage.getNumber())
                .size(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .hasNext(productPage.hasNext())
                .hasPrevious(productPage.hasPrevious())
                .build();
    }

    public ProductResponse getProduct(int id) {
        ProductEntity entity = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(String.format("No product exists with id: %s", id)));
        return ProductResponseMapper.toResponse(entity);
    }

    public List<String> getCategories() {
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "name"))
                .stream()
                .map(categoryEntity -> categoryEntity.getName())
                .toList();
    }

    private void validatePagination(int page, int size) {
        if (page < 0) {
            throw new InvalidProductException("Page must be greater than or equal to 0");
        }
        if (size <= 0) {
            throw new InvalidProductException("Size must be greater than 0");
        }
    }

    private Sort buildSort(String sortBy, String direction) {
        String requestedSortBy = (sortBy == null || sortBy.isBlank()) ? DEFAULT_SORT_BY : sortBy.trim();
        if (!ALLOWED_SORT_FIELDS.contains(requestedSortBy)) {
            throw new InvalidProductException("Invalid sort field: " + requestedSortBy);
        }

        Sort.Direction sortDirection = Sort.Direction.fromOptionalString(
                        direction == null || direction.isBlank() ? DEFAULT_DIRECTION : direction.trim())
                .orElseThrow(() -> new InvalidProductException("Invalid sort direction: " + direction));

        String property = switch (requestedSortBy) {
            case "imageUrl" -> "image";
            case "category" -> "category.name";
            default -> requestedSortBy;
        };

        return Sort.by(sortDirection, property);
    }

    private String normalizeCategory(String category) {
        if (category == null || category.isBlank()) {
            return null;
        }

        Category parsedCategory = Category.fromString(category.trim());
        if (parsedCategory.isUnknown()) {
            throw new InvalidProductException("Invalid category: " + category);
        }
        return parsedCategory.name();
    }
}
