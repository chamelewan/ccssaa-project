# Architecture Documentation

## System Architecture

The CCSSAA Project follows a layered architecture pattern based on Spring Boot best practices.

## Architecture Layers

```
┌─────────────────────────────────────────┐
│         Presentation Layer              │
│    (Controllers, REST Endpoints)        │
└─────────────────────────────────────────┘
                    │
                    ▼
┌─────────────────────────────────────────┐
│          Service Layer                  │
│    (Business Logic, Validation)         │
└─────────────────────────────────────────┘
                    │
                    ▼
┌─────────────────────────────────────────┐
│       Repository Layer                  │
│    (Data Access, JPA Repositories)      │
└─────────────────────────────────────────┘
                    │
                    ▼
┌─────────────────────────────────────────┐
│         Database Layer                  │
│           (MySQL)                       │
└─────────────────────────────────────────┘
```

## Layer Descriptions

### 1. Presentation Layer (Controllers)

**Purpose**: Handle HTTP requests and responses, request validation, and response formatting.

**Technologies**:
- Spring Web MVC
- Spring REST Controllers
- Bean Validation

**Responsibilities**:
- Receive and validate HTTP requests
- Call appropriate service methods
- Format and return HTTP responses
- Handle exceptions and error responses

**Example Package**: `com.ccssaa.project.controller`

### 2. Service Layer

**Purpose**: Implement business logic and orchestrate operations.

**Technologies**:
- Spring Service Components
- Transaction Management

**Responsibilities**:
- Implement business rules and logic
- Coordinate between controllers and repositories
- Handle transactions
- Implement security checks
- Perform data transformation

**Example Package**: `com.ccssaa.project.service`

### 3. Repository Layer

**Purpose**: Data access and persistence operations.

**Technologies**:
- Spring Data JPA
- Hibernate ORM
- JDBC

**Responsibilities**:
- CRUD operations
- Custom queries
- Database interactions
- Entity management

**Example Package**: `com.ccssaa.project.repository`

### 4. Domain/Model Layer

**Purpose**: Define data structures and business entities.

**Technologies**:
- JPA Entities
- Lombok for reducing boilerplate

**Responsibilities**:
- Define entity structure
- Define relationships
- Validation constraints
- DTOs (Data Transfer Objects)

**Example Package**: `com.ccssaa.project.model` or `com.ccssaa.project.entity`

## Cross-Cutting Concerns

### Security

**Implementation**: Spring Security

**Features**:
- Authentication (username/password, JWT)
- Authorization (role-based access control)
- CSRF protection
- Session management
- Password encryption (BCrypt)

**Package**: `com.ccssaa.project.security`

### Exception Handling

**Implementation**: Global exception handler

**Responsibilities**:
- Centralized error handling
- Custom exception types
- Consistent error responses
- Logging

**Package**: `com.ccssaa.project.exception`

### Validation

**Implementation**: Bean Validation (JSR-380)

**Features**:
- Request parameter validation
- Entity validation
- Custom validators
- Error message formatting

### Logging

**Implementation**: SLF4J with Logback

**Log Levels**:
- ERROR: Critical issues
- WARN: Potential problems
- INFO: General information
- DEBUG: Debugging information
- TRACE: Detailed trace information

## Design Patterns

### 1. Dependency Injection

Spring's IoC container manages dependencies and bean lifecycle.

### 2. Repository Pattern

Spring Data JPA provides repository pattern implementation for data access.

### 3. DTO Pattern

Data Transfer Objects for transferring data between layers.

### 4. Builder Pattern

Lombok's `@Builder` annotation for object construction.

### 5. Singleton Pattern

Spring beans are singletons by default.

## Package Structure

```
com.ccssaa.project/
├── CcssaaProjectApplication.java
├── controller/
│   ├── UserController.java
│   ├── RoleController.java
│   └── AuthController.java
├── service/
│   ├── UserService.java
│   ├── RoleService.java
│   └── AuthService.java
├── repository/
│   ├── UserRepository.java
│   ├── RoleRepository.java
│   └── UserRoleRepository.java
├── model/
│   ├── entity/
│   │   ├── User.java
│   │   ├── Role.java
│   │   └── UserRole.java
│   └── dto/
│       ├── UserDTO.java
│       ├── RoleDTO.java
│       └── AuthRequestDTO.java
├── security/
│   ├── SecurityConfig.java
│   ├── JwtTokenProvider.java
│   └── UserDetailsServiceImpl.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   └── UnauthorizedException.java
├── config/
│   ├── DatabaseConfig.java
│   └── WebConfig.java
└── util/
    ├── DateUtil.java
    └── ValidationUtil.java
```

## Configuration

### Application Properties

Located at `src/main/resources/application.properties`

**Key Configurations**:
- Database connection
- JPA/Hibernate settings
- Security settings
- Logging configuration
- Server port and context path

### Environment-Specific Configuration

Support for multiple environments:
- `application-dev.properties` - Development
- `application-test.properties` - Testing
- `application-prod.properties` - Production

Activate with: `spring.profiles.active=dev`

## API Design

### RESTful Principles

- Use appropriate HTTP methods (GET, POST, PUT, DELETE, PATCH)
- Resource-based URLs
- Stateless communication
- Standard HTTP status codes
- JSON request/response format

### API Versioning

Recommended approach: URL versioning

```
/api/v1/users
/api/v1/roles
```

### Response Format

Standard JSON response structure:

```json
{
  "status": "success",
  "data": { ... },
  "message": "Operation completed successfully"
}
```

Error response structure:

```json
{
  "status": "error",
  "error": {
    "code": "USER_NOT_FOUND",
    "message": "User with id 123 not found",
    "timestamp": "2026-02-01T11:39:43Z"
  }
}
```

## Security Architecture

### Authentication Flow

1. User submits credentials
2. System validates credentials
3. Generate JWT token
4. Return token to client
5. Client includes token in subsequent requests
6. System validates token and extracts user information

### Authorization

Role-based access control (RBAC):
- Users have one or more roles
- Endpoints require specific roles
- Spring Security filters enforce access control

## Testing Strategy

### Unit Tests

- Test individual components in isolation
- Mock dependencies
- Use JUnit 5 and Mockito

### Integration Tests

- Test component interactions
- Use test database (H2 in-memory)
- Spring Boot Test framework

### API Tests

- Test REST endpoints
- Use MockMvc or RestAssured
- Verify request/response

## Performance Considerations

1. **Database Optimization**
   - Use appropriate indexes
   - Optimize queries
   - Connection pooling

2. **Caching**
   - Spring Cache abstraction
   - Redis for distributed cache

3. **Lazy Loading**
   - JPA lazy loading for relationships
   - Avoid N+1 query problem

4. **Pagination**
   - Use Spring Data pagination
   - Limit result set size

## Deployment

### Build

```bash
./gradlew clean build
```

### Package

Creates executable JAR:
```bash
java -jar build/libs/ccssaa-project-0.0.1-SNAPSHOT.jar
```

### Deployment Options

1. **Traditional Server**: Deploy JAR on application server
2. **Container**: Docker containerization
3. **Cloud**: Deploy to cloud platforms (AWS, Azure, GCP)

## Monitoring and Observability

### Spring Boot Actuator

Health checks, metrics, and monitoring endpoints:
- `/actuator/health`
- `/actuator/metrics`
- `/actuator/info`

### Logging

Centralized logging with appropriate log levels and structured logging.

## Future Enhancements

1. Implement caching layer (Redis)
2. Add message queue (RabbitMQ/Kafka)
3. Implement API documentation (Swagger/OpenAPI)
4. Add monitoring (Prometheus, Grafana)
5. Implement CI/CD pipeline
6. Add microservices architecture
