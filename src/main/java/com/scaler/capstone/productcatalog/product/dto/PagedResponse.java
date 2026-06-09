package com.scaler.capstone.productcatalog.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PagedResponse<T> {
    List<T> content;
    @Schema(example = "0")
    int page;
    @Schema(example = "10")
    int size;
    @Schema(example = "24")
    long totalElements;
    @Schema(example = "3")
    int totalPages;
    boolean hasNext;
    boolean hasPrevious;
}
