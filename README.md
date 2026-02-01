# CCSSAA Project

## Project Overview

This is a Spring Boot application project for the CCSSAA system. It's built using Java 17 and Spring Boot 3.5.10, providing a robust foundation for enterprise-level applications.

## Technology Stack

- **Java**: 17
- **Spring Boot**: 3.5.10
- **Build Tool**: Gradle
- **Database**: MySQL
- **ORM**: Spring Data JPA
- **Security**: Spring Security
- **Validation**: Spring Validation
- **Development Tools**: Spring DevTools, Lombok

## Prerequisites

- Java 17 or higher
- MySQL database
- Gradle (or use the included Gradle wrapper)

## Project Structure

```
ccssaa-project/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/ccssaa/project/
│   │   │       └── CcssaaProjectApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/ccssaa/project/
│               └── CcssaaProjectApplicationTests.java
├── build.gradle
├── settings.gradle
└── README.md
```

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/chamelewan/ccssaa-project.git
cd ccssaa-project
```

### 2. Configure Database

Update `src/main/resources/application.properties` with your MySQL database credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ccssaa_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. Build the Project

Using Gradle wrapper (recommended):

```bash
./gradlew build
```

Or using Gradle:

```bash
gradle build
```

### 4. Run the Application

```bash
./gradlew bootRun
```

Or after building:

```bash
java -jar build/libs/ccssaa-project-0.0.1-SNAPSHOT.jar
```

### 5. Run Tests

```bash
./gradlew test
```

## Documentation

For detailed documentation, please refer to the [docs](./docs) directory:

- [Architecture Documentation](./docs/ARCHITECTURE.md)
- [Database Schema & ERD](./docs/ERD.md)
- [API Documentation](./docs/API.md)
- [Development Guide](./docs/DEVELOPMENT.md)

## Features

This project includes the following Spring Boot features:

- **Data Persistence**: Spring Data JPA for database operations
- **Security**: Spring Security for authentication and authorization
- **Validation**: Bean validation for input validation
- **REST API**: Spring Web for building RESTful services
- **Development Tools**: Hot reload and enhanced development experience

## Dependencies

Key dependencies included in this project:

- `spring-boot-starter-data-jpa` - Database persistence
- `spring-boot-starter-security` - Security features
- `spring-boot-starter-validation` - Input validation
- `spring-boot-starter-web` - Web and REST API support
- `mysql-connector-j` - MySQL database driver
- `lombok` - Reduce boilerplate code
- `spring-boot-devtools` - Development tools
- `spring-boot-starter-test` - Testing support
- `spring-security-test` - Security testing support

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is part of the CCSSAA initiative.

## Contact

For questions or support, please contact the development team.
