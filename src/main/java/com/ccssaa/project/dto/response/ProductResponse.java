package com.ccssaa.project.dto.response;

import com.ccssaa.project.domain.Product;
import com.ccssaa.project.domain.enums.ProductCategory;
import com.ccssaa.project.domain.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String title;
    private String description;
    private Integer price;
    private ProductCategory category;
    private ProductStatus status;
    private String location;
    private Integer viewCount;
    private Long sellerId;
    private String sellerNickname;
    private LocalDateTime createdAt;

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .status(product.getStatus())
                .location(product.getLocation())
                .viewCount(product.getViewCount())
                .sellerId(product.getSeller().getId())
                .sellerNickname(product.getSeller().getNickname())
                .createdAt(product.getCreatedAt())
                .build();
    }
}
