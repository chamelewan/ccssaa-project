# 중고 거래 플랫폼 (ccssaa-project)

## 📌 프로젝트 개요

ICT B트랙 자율 주제 프로젝트 - 당근마켓 스타일의 중고 거래 플랫폼

사용자 간 중고 물품 거래를 중개하는 플랫폼으로, 상품 등록, 거래, 채팅, 리뷰 기능을 제공합니다.

---

## 🛠️ 기술 스택

- **Backend**: Spring Boot 3.x
- **Security**: Spring Security + JWT
- **Database**: MySQL 8.0
- **ORM**: JPA/Hibernate
- **API Documentation**: SpringDoc OpenAPI (Swagger UI)
- **Build Tool**: Gradle
- **Language**: Java 17

---

## ✅ 과제 요구사항 충족

### 필수 요구사항

| 요구사항 | 충족 방법 | 비고 |
|---------|----------|------|
| **상태 전이** | Product: `SELLING → RESERVED → SOLD`<br>Transaction: `REQUESTED → CONFIRMED → COMPLETED` | Enum으로 관리, 비즈니스 로직에서 전이 규칙 강제 |
| **권한 분리** | User role: `USER`, `ADMIN`<br>판매자/구매자 권한 분리 | Spring Security로 구현 |
| **동시성 이슈** | 동일 상품 동시 구매 요청 시 1개만 성공 | 낙관적 락(Optimistic Lock) 적용 |
| **데이터 무결성** | 거래 생성 시 상품 상태 변경 원자성 보장<br>중복 찜/리뷰 방지 | @Transactional + UNIQUE 제약 |

### 비즈니스 규칙
- ✅ 상품 상태에 따른 거래 가능 여부 검증
- ✅ 본인 상품 구매 방지
- ✅ 거래 완료 후에만 리뷰 작성 가능
- ✅ 동시 구매 요청 시 충돌 방지

---

## 📊 ERD

전체 ERD 문서: [ERD.md](./docs/ERD.md)

### 핵심 테이블
- **User**: 사용자 (권한: USER, ADMIN)
- **Product**: 상품 (상태: SELLING, RESERVED, SOLD)
- **Transaction**: 거래 (동시성 제어 적용)
- **ChatRoom / ChatMessage**: 실시간 채팅
- **Review**: 거래 후 평가
- **Wishlist**: 관심 상품

---

## 📁 프로젝트 구조

```
ccssaa-project/
├── src/
│   ├── main/
│   │   ├── java/com/ccssaa/project/
│   │   │   ├── domain/          # 엔티티 (User, Product, Transaction 등)
│   │   │   ├── repository/      # JPA Repository
│   │   │   ├── service/         # 비즈니스 로직
│   │   │   ├── controller/      # REST API
│   │   │   ├── dto/             # Request/Response DTO
│   │   │   ├── config/          # 설정 (Security, JWT)
│   │   │   ├── common/          # 공통 (예외, 응답 형식)
│   │   │   └── exception/       # 커스텀 예외
│   │   └── resources/
│   │       ├── application.yml
│   │       └── application-dev.yml
│   └── test/
├── docs/
│   └── ERD.md
├── build.gradle
└── README.md
```

---

## 🔑 핵심 설계 의사결정

### 1. 인증 방식
- **JWT 토큰 기반 인증** 선택
- 이유: Stateless, 확장성, 모바일 앱 지원 용이

### 2. 동시성 제어
- **낙관적 락(Optimistic Lock)** 사용
- 이유: 충돌이 적은 환경에서 성능 우수, `@Version` 어노테이션으로 간단히 구현

### 3. 트랜잭션 범위
- **서비스 레이어에 @Transactional** 적용
- 거래 생성 시 상품 상태 변경을 하나의 트랜잭션으로 처리

### 4. 상태 관리
- **Enum 타입**으로 상태 정의
- 비즈니스 로직에서 허용된 전이만 가능하도록 검증

---

## 🚀 실행 방법

### 1. 데이터베이스 설정

#### 프로덕션 환경 (MySQL)
```sql
CREATE DATABASE ccssaa_db;
```

#### 테스트 환경 (H2 인메모리 DB)
별도 설정 불필요 - 자동으로 H2 데이터베이스 사용

### 2. application.yml 설정

프로덕션 환경에서는 `src/main/resources/application.yml` 파일의 데이터베이스 설정 수정:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ccssaa_db
    username: your_username
    password: your_password
```

### 3. 빌드 및 실행

#### 프로덕션 환경
```bash
./gradlew clean build
./gradlew bootRun
```

#### 테스트 환경 (H2 DB 사용)
```bash
./gradlew bootRun --args='--spring.profiles.active=test'
```

### 4. Swagger UI 접속

애플리케이션 실행 후 브라우저에서 다음 URL로 접속:

**Swagger UI**: http://localhost:8080/swagger-ui/index.html

또는

**Alternative URL**: http://localhost:8080/swagger-ui.html

**API Docs (JSON)**: http://localhost:8080/v3/api-docs

자세한 Swagger UI 사용 방법은 [Swagger UI 가이드](./docs/SWAGGER_UI_GUIDE.md)를 참고하세요.

---

## 📝 API 명세

### Swagger UI로 확인

애플리케이션 실행 후 **Swagger UI**에서 모든 API 명세를 확인할 수 있습니다:

- **URL**: http://localhost:8080/swagger-ui/index.html
- **상세 가이드**: [Swagger UI 사용 가이드](./docs/SWAGGER_UI_GUIDE.md)

### 주요 API 엔드포인트

#### User API (사용자 API)
- `POST /api/users/register` - 회원가입
- `POST /api/users/login` - 로그인 (JWT 토큰 발급)

#### Product API (상품 API)
- `GET /api/products` - 전체 상품 목록 조회
- `POST /api/products` - 상품 등록
- `GET /api/products/{id}` - 상품 상세 조회
- `GET /api/products/selling` - 판매중인 상품 목록 조회

모든 API는 Swagger UI에서 직접 테스트할 수 있습니다.

---

## 👥 Contributors

- [@chamelewan](https://github.com/chamelewan) - Seung Wan KIM

---

## 📄 License

This project is licensed under the MIT License.
