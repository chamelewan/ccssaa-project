package com.ccssaa.project.domain;

import com.ccssaa.project.domain.enums.ProductCategory;
import com.ccssaa.project.domain.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;
    
    @Column(nullable = false, length = 255)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(nullable = false)
    private Integer price;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private ProductCategory category;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private ProductStatus status = ProductStatus.SELLING;
    
    @Column(length = 255)
    private String location;
    
    @Column(nullable = false)
    @Builder.Default
    private Integer viewCount = 0;
    
    @Version
    private Long version; // 낙관적 락을 위한 버전 필드
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    // 비즈니스 메서드
    public void updateStatus(ProductStatus newStatus) {
        // 상태 전이 규칙 검증
        if (this.status == ProductStatus.SELLING && newStatus == ProductStatus.RESERVED) {
            this.status = newStatus;
        } else if (this.status == ProductStatus.RESERVED && newStatus == ProductStatus.SOLD) {
            this.status = newStatus;
        } else if (this.status == ProductStatus.RESERVED && newStatus == ProductStatus.SELLING) {
            this.status = newStatus;
        } else if (newStatus == ProductStatus.DELETED) {
            this.status = newStatus;
        } else {
            throw new IllegalStateException("Invalid status transition from " + this.status + " to " + newStatus);
        }
    }
    
    public void incrementViewCount() {
        this.viewCount++;
    }
}
