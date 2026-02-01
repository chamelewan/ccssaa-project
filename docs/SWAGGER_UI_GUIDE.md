# Swagger UI 사용 가이드

## Swagger UI 접속 방법

애플리케이션을 실행한 후, 다음 URL로 접속할 수 있습니다:

**Swagger UI**: http://localhost:8080/swagger-ui/index.html

또는

**Alternative URL**: http://localhost:8080/swagger-ui.html

## Swagger UI 화면 설명

### ✅ 정상적으로 표시되는 화면

Swagger UI에 접속하면 다음과 같은 내용이 표시됩니다:

1. **API 제목**: "중고 거래 플랫폼 API 1.0.0"
2. **API 설명**: "ICT B트랙 자율 주제 프로젝트 - 중고 거래 플랫폼 API 명세서"
3. **서버 URL**: http://localhost:8080
4. **Authorize 버튼**: JWT 토큰 인증을 위한 버튼

### API 엔드포인트

#### Product API (상품 API)
- `GET /api/products` - 전체 상품 목록 조회
- `POST /api/products` - 상품 등록
- `GET /api/products/{id}` - 상품 상세 조회
- `GET /api/products/selling` - 판매중인 상품 목록 조회

#### User API (사용자 API)
- `POST /api/users/login` - 로그인
- `POST /api/users/register` - 회원가입

### Schemas
API 요청/응답에 사용되는 모든 데이터 모델 스키마가 하단에 표시됩니다.

## Swagger UI 사용 방법

### 1. API 테스트하기

각 API 엔드포인트를 클릭하면 다음 작업을 수행할 수 있습니다:

1. **Try it out** 버튼 클릭
2. 필요한 파라미터 입력
3. **Execute** 버튼 클릭
4. 응답 결과 확인

### 2. JWT 인증 사용하기

인증이 필요한 API를 테스트하려면:

1. 우측 상단의 **Authorize** 버튼 클릭
2. JWT 토큰 입력 (형식: `Bearer <token>`)
3. **Authorize** 클릭
4. 인증된 상태로 API 테스트

### 3. 회원가입 및 로그인 테스트 예시

#### 회원가입
```json
POST /api/users/register
{
  "email": "test@example.com",
  "password": "password123",
  "nickname": "테스트사용자",
  "phone": "010-1234-5678",
  "address": "서울시 강남구"
}
```

#### 로그인
```json
POST /api/users/login
{
  "email": "test@example.com",
  "password": "password123"
}
```

로그인 성공 시 받은 JWT 토큰을 **Authorize** 버튼에서 설정하면 인증이 필요한 API를 사용할 수 있습니다.

## API 문서 JSON 형식

OpenAPI 3.0 스펙 JSON 문서는 다음 URL에서 확인할 수 있습니다:

**API Docs**: http://localhost:8080/v3/api-docs

## 문제 해결

### Swagger UI가 표시되지 않는 경우

1. 애플리케이션이 실행 중인지 확인
   ```bash
   ./gradlew bootRun
   ```

2. 8080 포트가 사용 가능한지 확인
   ```bash
   lsof -i :8080
   ```

3. 브라우저 캐시 삭제 후 재접속

### 데이터베이스 연결 오류

테스트 환경에서는 H2 데이터베이스를 사용합니다:
```bash
./gradlew bootRun --args='--spring.profiles.active=test'
```

또는 `application-test.yml` 프로파일을 사용하여 실행

## 추가 정보

- Swagger UI 버전: springdoc-openapi-starter-webmvc-ui:2.7.0
- OpenAPI 버전: 3.0.1
- Spring Boot 버전: 3.5.10
