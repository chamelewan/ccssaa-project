# 중고 거래 플랫폼

## 프로젝트 개요

**중고 거래 플랫폼**은 ICT B트랙 자율 주제 프로젝트로 개발된 당근마켓 스타일의 중고 거래 애플리케이션입니다. 사용자 간의 안전한 중고 거래를 지원하며, 실시간 채팅, 리뷰 시스템, 찜 기능 등을 제공합니다.

### 주요 기능
- 🛍️ 상품 등록 및 관리 (이미지 업로드, 카테고리 분류)
- 💬 실시간 채팅 시스템
- 🤝 거래 요청 및 관리
- ⭐ 거래 후 리뷰 작성
- ❤️ 관심 상품 찜하기
- 👤 사용자 관리 (회원가입, 로그인, 프로필)
- 🔒 권한 관리 (일반 사용자, 관리자)

---

## 기술 스택

### Backend
- **Framework**: Spring Boot 3.5.10
- **Language**: Java 17
- **Security**: Spring Security, JWT
- **ORM**: JPA/Hibernate
- **Database**: MySQL
- **Build Tool**: Gradle

### Dependencies
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- Spring Boot Starter Validation
- MySQL Connector
- Lombok

---

## 과제 요구사항 충족 내용

### 필수 요구사항 충족 여부

| 요구사항 | 충족 방법 | 상세 설명 |
|---------|----------|----------|
| **상태 전이** | Product 상태 전이<br>Transaction 상태 전이 | • Product: `SELLING` → `RESERVED` → `SOLD`<br>• Transaction: `REQUESTED` → `CONFIRMED` → `COMPLETED`<br>• 각 상태는 Service 계층에서 검증 및 제어 |
| **권한 분리** | User role 기반 권한 관리 | • USER: 일반 사용자 (상품 등록, 구매, 채팅, 리뷰)<br>• ADMIN: 관리자 (모든 데이터 조회/관리, 사용자 정지)<br>• Spring Security + JWT로 인증/인가 처리 |
| **동시성 이슈** | 낙관적 락 (Optimistic Lock) | • 동일 상품에 동시 구매 요청 시 JPA `@Version` 활용<br>• 먼저 성공한 거래만 CONFIRMED, 나머지 실패<br>• Product 상태 변경의 원자성 보장 |
| **데이터 무결성** | 트랜잭션 및 제약조건 | • Transaction 생성 시 Product 상태 변경의 원자성 보장<br>• UNIQUE 제약: 중복 찜/리뷰 방지<br>• FK 제약: 참조 무결성 보장<br>• Cascade 삭제: 연관 데이터 자동 삭제 |

### 추가 구현 사항

- ✅ **8개 테이블 ERD 설계**: User, Product, ProductImage, Transaction, ChatRoom, ChatMessage, Review, Wishlist
- ✅ **상세 비즈니스 규칙 정의**: 상태 전이, 권한, 거래 프로세스, 채팅, 리뷰
- ✅ **보안 고려**: 비밀번호 암호화, JWT 인증, SQL Injection 방지
- ✅ **성능 최적화**: 인덱스 설계, 페이징 처리

---

## 프로젝트 구조

```
ccssaa-project/
├── src/
│   ├── main/
│   │   ├── java/com/ccssaa/project/
│   │   │   ├── domain/          # 도메인 엔티티
│   │   │   │   ├── User.java
│   │   │   │   ├── Product.java
│   │   │   │   ├── ProductImage.java
│   │   │   │   ├── Transaction.java
│   │   │   │   ├── ChatRoom.java
│   │   │   │   ├── ChatMessage.java
│   │   │   │   ├── Review.java
│   │   │   │   └── Wishlist.java
│   │   │   ├── repository/      # JPA Repository
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── ProductRepository.java
│   │   │   │   ├── TransactionRepository.java
│   │   │   │   └── ...
│   │   │   ├── service/         # 비즈니스 로직
│   │   │   │   ├── UserService.java
│   │   │   │   ├── ProductService.java
│   │   │   │   ├── TransactionService.java
│   │   │   │   └── ...
│   │   │   ├── controller/      # REST API
│   │   │   │   ├── UserController.java
│   │   │   │   ├── ProductController.java
│   │   │   │   ├── TransactionController.java
│   │   │   │   └── ...
│   │   │   ├── dto/             # DTO (Request/Response)
│   │   │   │   ├── request/
│   │   │   │   └── response/
│   │   │   ├── config/          # 설정 (Security, JWT 등)
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   ├── JwtConfig.java
│   │   │   │   └── WebConfig.java
│   │   │   └── common/          # 공통 (예외, 응답 등)
│   │   │       ├── exception/
│   │   │       ├── response/
│   │   │       └── util/
│   │   └── resources/
│   │       ├── application.yml
│   │       └── application-dev.yml
│   └── test/
│       └── java/com/ccssaa/project/
├── docs/
│   └── ERD.md                   # ERD 문서
├── build.gradle
└── README.md
```

---

## 문서

### 📊 ERD 문서
- [ERD 문서 보기](docs/ERD.md)
- 8개 테이블 상세 정의 및 관계
- Mermaid ERD 다이어그램
- 비즈니스 규칙 및 제약조건

---

## 핵심 비즈니스 규칙

### 1. 상품 상태 전이 규칙

```
SELLING (판매중)
   ↓ 거래 요청 수락
RESERVED (예약중)
   ↓ 거래 완료        ↓ 거래 취소
SOLD (판매완료)    SELLING (판매중)
   
ANY → DELETED (삭제)
```

### 2. 거래 상태 전이 규칙

```
REQUESTED (요청)
   ↓ 판매자 수락       ↓ 취소
CONFIRMED (확정)   CANCELED (취소)
   ↓ 거래 완료
COMPLETED (완료)
```

### 3. 동시성 제어 전략

**문제 상황**: 동일 상품에 여러 구매자가 동시에 거래 요청

**해결 방법**:
```java
@Entity
public class Product {
    @Version
    private Long version;  // 낙관적 락
    // ...
}

@Transactional
public Transaction createTransaction(CreateTransactionRequest request) {
    // 1. 상품 조회 (version 포함)
    Product product = productRepository.findById(productId);
    
    // 2. 상태 검증
    if (product.getStatus() != ProductStatus.SELLING) {
        throw new IllegalStateException("판매중인 상품이 아닙니다.");
    }
    
    // 3. 상품 상태 변경 + 거래 생성 (원자적)
    product.changeStatus(ProductStatus.RESERVED);  // version 증가
    Transaction transaction = new Transaction(...);
    
    // 4. 저장 (version 충돌 시 예외 발생)
    productRepository.save(product);  // OptimisticLockException 가능
    transactionRepository.save(transaction);
    
    return transaction;
}
```

### 4. 트랜잭션 범위

- **거래 생성**: Product 상태 변경 + Transaction 생성 (단일 트랜잭션)
- **거래 완료**: Transaction 상태 변경 + Product 상태 변경 (단일 트랜잭션)
- **거래 취소**: Transaction 상태 변경 + Product 상태 복원 (단일 트랜잭션)

### 5. 데이터 무결성 보장

- **외래 키 제약**: 모든 관계에 FK 설정
- **Unique 제약**:
  - `(product_id, buyer_id)` in ChatRoom - 중복 채팅방 방지
  - `(transaction_id, reviewer_id)` in Review - 중복 리뷰 방지
  - `(user_id, product_id)` in Wishlist - 중복 찜 방지
- **Check 제약**: Review.rating (1~5)
- **Cascade 삭제**:
  - Product 삭제 → ProductImage 삭제
  - ChatRoom 삭제 → ChatMessage 삭제
  - User/Product 삭제 → Wishlist 삭제

---

## 개발 환경 설정

### 1. 사전 요구사항
- Java 17 이상
- MySQL 8.0 이상
- Gradle 7.x 이상

### 2. 데이터베이스 설정

```sql
CREATE DATABASE ccssaa_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'ccssaa_user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON ccssaa_db.* TO 'ccssaa_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. 애플리케이션 설정

`src/main/resources/application-dev.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ccssaa_db
    username: ccssaa_user
    password: password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

### 4. 실행

```bash
# 빌드
./gradlew build

# 실행
./gradlew bootRun

# 테스트
./gradlew test
```

---

## API 명세 (예정)

### User
- `POST /api/users/signup` - 회원가입
- `POST /api/users/login` - 로그인
- `GET /api/users/{id}` - 사용자 조회
- `PUT /api/users/{id}` - 사용자 정보 수정

### Product
- `POST /api/products` - 상품 등록
- `GET /api/products` - 상품 목록 조회 (페이징)
- `GET /api/products/{id}` - 상품 상세 조회
- `PUT /api/products/{id}` - 상품 수정
- `DELETE /api/products/{id}` - 상품 삭제

### Transaction
- `POST /api/transactions` - 거래 요청
- `PUT /api/transactions/{id}/confirm` - 거래 확정
- `PUT /api/transactions/{id}/complete` - 거래 완료
- `PUT /api/transactions/{id}/cancel` - 거래 취소

### Chat
- `POST /api/chatrooms` - 채팅방 생성
- `GET /api/chatrooms` - 채팅방 목록
- `POST /api/chatrooms/{id}/messages` - 메시지 전송
- `GET /api/chatrooms/{id}/messages` - 메시지 조회

### Review
- `POST /api/reviews` - 리뷰 작성
- `GET /api/reviews/user/{userId}` - 사용자 리뷰 조회

### Wishlist
- `POST /api/wishlists` - 찜하기
- `DELETE /api/wishlists/{id}` - 찜 취소
- `GET /api/wishlists` - 내 찜 목록

---

## 개발 계획

### Phase 1: 기본 기능 (진행 중)
- [x] ERD 설계
- [ ] Entity 구현
- [ ] Repository 구현
- [ ] 기본 CRUD API 구현

### Phase 2: 핵심 비즈니스 로직
- [ ] 거래 프로세스 구현
- [ ] 상태 전이 로직 구현
- [ ] 동시성 제어 구현

### Phase 3: 보안 및 인증
- [ ] Spring Security 설정
- [ ] JWT 인증 구현
- [ ] 권한 관리 구현

### Phase 4: 고급 기능
- [ ] 채팅 시스템 구현
- [ ] 리뷰 시스템 구현
- [ ] 찜 기능 구현

### Phase 5: 최적화 및 배포
- [ ] 성능 최적화
- [ ] 테스트 코드 작성
- [ ] 배포 설정

---

## 팀 정보

- **프로젝트 기간**: 2026.01 ~ 2026.02
- **팀 구성**: ICT B트랙
- **프로젝트 타입**: 자율 주제 프로젝트

---

## 라이선스

This project is licensed under the MIT License.

---

## 참고 자료

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Security](https://spring.io/projects/spring-security)
- [MySQL Documentation](https://dev.mysql.com/doc/)
