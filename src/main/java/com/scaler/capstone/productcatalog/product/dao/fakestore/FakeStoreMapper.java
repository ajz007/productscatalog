package com.scaler.capstone.productcatalog.product.dao.fakestore;

import com.scaler.capstone.productcatalog.product.model.Category;
import com.scaler.capstone.productcatalog.product.model.Product;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper Object acting as an adaptor between the Service Model and the FakeStore DTOs.
 */
public class FakeStoreMapper {

    /**
     * Map from Array {@link FakeStoreProductDTO} to Array {@link Product}
     *
     * @param fakeproducts
     * @return List of {@link Product}
     */
    public static List<Product> mapToProductList(FakeStoreProductDTO[] fakeproducts) {
        return Arrays.stream(fakeproducts)
                .map(FakeStoreMapper::mapToProduct)
                .collect(Collectors.toList());
    }

    /**
     * Map from {@link FakeStoreProductDTO} to {@link Product}
     *
     * @param fakeproduct fakeproduct
     * @return Product
     */
    public static Product mapToProduct(FakeStoreProductDTO fakeproduct) {
        return Product.builder()
                .withCategory(Category.fromString(fakeproduct.getCategory()))
                .withDescription(fakeproduct.getDescription())
                .withTitle(fakeproduct.getTitle())
                .withId(fakeproduct.getId())
                .withImage(fakeproduct.getImage())
                .withPrice(fakeproduct.getPrice())
                .build();
    }

    /**
     * Map to {@link FakeStoreProductDTO} from {@link Product}
     *
     * @param product
     * @return Product
     */
    public static FakeStoreProductDTO mapToFakeStoreDTO(Product product) {
        return FakeStoreProductDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .price(product.getPrice())
                .category(product.getCategory().name())
                .description(product.getDescription())
                .image(product.getImage())
                .build();
    }
}
