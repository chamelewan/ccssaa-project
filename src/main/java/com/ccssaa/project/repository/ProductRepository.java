package com.ccssaa.project.repository;

import com.ccssaa.project.domain.Product;
import com.ccssaa.project.domain.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatus(ProductStatus status);
    List<Product> findBySellerId(Long sellerId);
}
