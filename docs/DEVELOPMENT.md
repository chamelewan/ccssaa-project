# Development Guide

## Getting Started with Development

This guide will help you set up your development environment and get started with contributing to the CCSSAA Project.

## Prerequisites

Before you begin, ensure you have the following installed:

1. **Java Development Kit (JDK) 17**
   - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or use [OpenJDK](https://openjdk.org/)
   - Verify installation: `java -version`

2. **Gradle** (Optional - project includes Gradle wrapper)
   - Download from [Gradle.org](https://gradle.org/install/)
   - Verify installation: `gradle -version`

3. **MySQL Database**
   - Download from [MySQL.com](https://dev.mysql.com/downloads/)
   - Version 8.0 or higher recommended

4. **IDE** (Choose one)
   - IntelliJ IDEA (Recommended)
   - Eclipse
   - VS Code with Java extensions

5. **Git**
   - Download from [git-scm.com](https://git-scm.com/)
   - Verify installation: `git --version`

## Environment Setup

### 1. Clone the Repository

```bash
git clone https://github.com/chamelewan/ccssaa-project.git
cd ccssaa-project
```

### 2. Set Up MySQL Database

Create a new database for the project:

```sql
CREATE DATABASE ccssaa_db;
CREATE USER 'ccssaa_user'@'localhost' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON ccssaa_db.* TO 'ccssaa_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configure Application Properties

Create or update `src/main/resources/application.properties`:

```properties
# Application Name
spring.application.name=ccssaa-project

# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/ccssaa_db
spring.datasource.username=ccssaa_user
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Logging Configuration
logging.level.root=INFO
logging.level.com.ccssaa.project=DEBUG
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} - %msg%n

# Security Configuration (to be implemented)
# jwt.secret=your-secret-key
# jwt.expiration=86400000
```

### 4. Build the Project

Using Gradle wrapper (recommended):

```bash
./gradlew clean build
```

Or using Gradle:

```bash
gradle clean build
```

### 5. Run the Application

```bash
./gradlew bootRun
```

Or:

```bash
java -jar build/libs/ccssaa-project-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

## Development Workflow

### 1. Create a New Branch

Always create a feature branch for new work:

```bash
git checkout -b feature/your-feature-name
```

Branch naming conventions:
- `feature/` - New features
- `bugfix/` - Bug fixes
- `hotfix/` - Critical fixes
- `refactor/` - Code refactoring
- `docs/` - Documentation updates

### 2. Make Your Changes

Follow the coding standards and best practices outlined below.

### 3. Test Your Changes

Run unit tests:

```bash
./gradlew test
```

Run integration tests:

```bash
./gradlew integrationTest
```

### 4. Commit Your Changes

```bash
git add .
git commit -m "Brief description of changes"
```

Commit message format:
```
<type>: <subject>

<body>

<footer>
```

Types:
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation
- `style`: Formatting
- `refactor`: Code refactoring
- `test`: Adding tests
- `chore`: Maintenance

Example:
```
feat: Add user authentication endpoint

Implemented JWT-based authentication with login and logout endpoints.
Added UserDetailsService implementation for Spring Security.

Closes #123
```

### 5. Push and Create Pull Request

```bash
git push origin feature/your-feature-name
```

Then create a Pull Request on GitHub.

## Coding Standards

### Java Coding Conventions

Follow standard Java naming conventions:

- **Classes**: PascalCase (e.g., `UserService`)
- **Methods**: camelCase (e.g., `getUserById`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_RETRY_COUNT`)
- **Packages**: lowercase (e.g., `com.ccssaa.project.service`)

### Code Style

- Use 4 spaces for indentation (no tabs)
- Maximum line length: 120 characters
- Always use braces for control structures
- Add JavaDoc comments for public methods
- Keep methods short and focused (single responsibility)

### Lombok Usage

Use Lombok to reduce boilerplate code:

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String username;
    private String email;
}
```

### Exception Handling

Create custom exceptions:

```java
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User not found with id: " + id);
    }
}
```

Use global exception handler:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        // Handle exception
    }
}
```

## Testing Guidelines

### Unit Tests

Test individual components in isolation:

```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    void testGetUserById() {
        // Arrange
        User user = User.builder().id(1L).username("test").build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        
        // Act
        User result = userService.getUserById(1L);
        
        // Assert
        assertNotNull(result);
        assertEquals("test", result.getUsername());
    }
}
```

### Integration Tests

Test component interactions:

```java
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void testGetUser() throws Exception {
        mockMvc.perform(get("/api/v1/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value("test"));
    }
}
```

### Test Coverage

Aim for at least 80% code coverage. Run coverage report:

```bash
./gradlew test jacocoTestReport
```

View report at: `build/reports/jacoco/test/html/index.html`

## Database Management

### Schema Updates

For development, use `spring.jpa.hibernate.ddl-auto=update` to automatically update schema.

For production, use migration tools:
- **Flyway**: SQL-based migrations
- **Liquibase**: XML/YAML-based migrations

### Database Seeding

Create a data initialization class:

```java
@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public void run(String... args) {
        // Initialize default data
    }
}
```

## Debugging

### IntelliJ IDEA

1. Set breakpoints in your code
2. Run application in Debug mode
3. Use Debug console to inspect variables

### Remote Debugging

Add JVM arguments:

```bash
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -jar app.jar
```

Connect debugger to port 5005.

### Logging

Add debug logging:

```java
@Slf4j
@Service
public class UserService {
    public User getUserById(Long id) {
        log.debug("Fetching user with id: {}", id);
        // ...
    }
}
```

## Common Development Tasks

### Adding a New Entity

1. Create entity class with JPA annotations
2. Create repository interface
3. Create service class
4. Create controller
5. Add validation
6. Write tests
7. Update documentation

### Adding a New Endpoint

1. Define method in controller
2. Implement service logic
3. Add validation
4. Write tests
5. Update API documentation

### Updating Dependencies

Edit `build.gradle`:

```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    // Add new dependency
}
```

Run:

```bash
./gradlew build --refresh-dependencies
```

## Performance Optimization

### Database Optimization

- Use appropriate indexes
- Avoid N+1 queries (use JOIN FETCH)
- Use pagination for large result sets
- Enable query caching

### Application Optimization

- Use lazy loading appropriately
- Implement caching (Redis)
- Optimize JSON serialization
- Use async processing for long tasks

## Security Best Practices

1. **Never commit sensitive data** (passwords, API keys)
2. **Validate all inputs** (use Bean Validation)
3. **Use parameterized queries** (prevent SQL injection)
4. **Encrypt sensitive data** (passwords, tokens)
5. **Implement rate limiting**
6. **Use HTTPS** in production

## Troubleshooting

### Port Already in Use

Change port in `application.properties`:

```properties
server.port=8081
```

### Database Connection Issues

- Verify MySQL is running
- Check credentials in `application.properties`
- Ensure database exists
- Check firewall settings

### Build Failures

- Clear Gradle cache: `./gradlew clean`
- Refresh dependencies: `./gradlew build --refresh-dependencies`
- Check Java version: `java -version`

## Useful Commands

```bash
# Build project
./gradlew build

# Run tests
./gradlew test

# Run application
./gradlew bootRun

# Clean build
./gradlew clean build

# Skip tests
./gradlew build -x test

# Generate JAR
./gradlew bootJar

# Check dependencies
./gradlew dependencies

# View all tasks
./gradlew tasks
```

## IDE Setup

### IntelliJ IDEA

1. Import project as Gradle project
2. Enable Lombok plugin
3. Enable annotation processing
4. Set code style to match project standards
5. Configure formatter settings

### VS Code

Install extensions:
- Extension Pack for Java
- Spring Boot Extension Pack
- Lombok Annotations Support

## Resources

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Spring Security](https://docs.spring.io/spring-security/reference/)
- [Gradle User Guide](https://docs.gradle.org/current/userguide/userguide.html)
- [MySQL Documentation](https://dev.mysql.com/doc/)

## Getting Help

- Check existing documentation
- Search GitHub issues
- Ask in team chat
- Create a new issue with detailed description

## Contributing Guidelines

1. Follow coding standards
2. Write tests for new code
3. Update documentation
4. Keep commits focused and atomic
5. Write clear commit messages
6. Ensure all tests pass before submitting PR
7. Request code review from team members
