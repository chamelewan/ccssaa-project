package com.ccssaa.project.controller;

import com.ccssaa.project.common.response.ApiResponse;
import com.ccssaa.project.dto.request.ProductCreateRequest;
import com.ccssaa.project.dto.response.ProductResponse;
import com.ccssaa.project.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product", description = "상품 API")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(
            @RequestParam Long sellerId,
            @Valid @RequestBody ProductCreateRequest request) {
        ProductResponse response = productService.createProduct(sellerId, request);
        return ResponseEntity.ok(ApiResponse.success("상품이 등록되었습니다.", response));
    }

    @Operation(summary = "전체 상품 목록 조회", description = "등록된 모든 상품을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts() {
        List<ProductResponse> response = productService.getAllProducts();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "판매중인 상품 목록 조회", description = "판매중(SELLING) 상태인 상품만 조회합니다.")
    @GetMapping("/selling")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getSellingProducts() {
        List<ProductResponse> response = productService.getSellingProducts();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "상품 상세 조회", description = "상품 ID로 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProduct(@PathVariable Long id) {
        ProductResponse response = productService.getProductById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
