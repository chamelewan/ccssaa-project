package com.ccssaa.project.domain;

import com.ccssaa.project.domain.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private User buyer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private TransactionStatus status = TransactionStatus.REQUESTED;
    
    @Column(length = 255)
    private String meetingLocation;
    
    private LocalDateTime meetingTime;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    private LocalDateTime completedAt;
    
    // 비즈니스 메서드
    public void confirm() {
        if (this.status != TransactionStatus.REQUESTED) {
            throw new IllegalStateException("Only REQUESTED transaction can be confirmed");
        }
        this.status = TransactionStatus.CONFIRMED;
    }
    
    public void complete() {
        if (this.status != TransactionStatus.CONFIRMED) {
            throw new IllegalStateException("Only CONFIRMED transaction can be completed");
        }
        this.status = TransactionStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }
    
    public void cancel() {
        if (this.status == TransactionStatus.COMPLETED) {
            throw new IllegalStateException("COMPLETED transaction cannot be canceled");
        }
        this.status = TransactionStatus.CANCELED;
    }
    
    @PrePersist
    private void validateTransaction() {
        if (buyer == null || seller == null) {
            throw new IllegalArgumentException("Buyer and seller must not be null");
        }
        if (buyer.getId().equals(seller.getId())) {
            throw new IllegalArgumentException("Buyer and seller cannot be the same user");
        }
    }
}
