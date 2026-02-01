# Entity Relationship Diagram (ERD)

## Database Schema Design

This document describes the database schema for the CCSSAA project, including entity relationships and database structure.

## Database Overview

The CCSSAA project uses **MySQL** as its primary database with **Spring Data JPA** for ORM (Object-Relational Mapping).

## ERD Diagram

```
┌─────────────────────────────┐
│         User                │
├─────────────────────────────┤
│ PK  id: BIGINT             │
│     username: VARCHAR(50)   │
│     email: VARCHAR(100)     │
│     password: VARCHAR(255)  │
│     firstName: VARCHAR(50)  │
│     lastName: VARCHAR(50)   │
│     enabled: BOOLEAN        │
│     createdAt: TIMESTAMP    │
│     updatedAt: TIMESTAMP    │
└─────────────────────────────┘
           │
           │ 1:N
           ▼
┌─────────────────────────────┐
│         Role                │
├─────────────────────────────┤
│ PK  id: BIGINT             │
│     name: VARCHAR(50)       │
│     description: VARCHAR    │
└─────────────────────────────┘
           ▲
           │ N:M
           │
┌─────────────────────────────┐
│      UserRole               │
├─────────────────────────────┤
│ PK  id: BIGINT             │
│ FK  userId: BIGINT          │
│ FK  roleId: BIGINT          │
│     assignedAt: TIMESTAMP   │
└─────────────────────────────┘
```

## Entity Descriptions

### User Entity

The User entity represents users of the CCSSAA system.

**Attributes:**
- `id` (Primary Key): Unique identifier for each user
- `username`: Unique username for authentication
- `email`: User's email address (unique)
- `password`: Encrypted password (using BCrypt)
- `firstName`: User's first name
- `lastName`: User's last name
- `enabled`: Account status (active/inactive)
- `createdAt`: Timestamp of account creation
- `updatedAt`: Timestamp of last update

**Relationships:**
- One User can have many Roles (Many-to-Many through UserRole)

### Role Entity

The Role entity defines authorization roles in the system.

**Attributes:**
- `id` (Primary Key): Unique identifier for each role
- `name`: Role name (e.g., ADMIN, USER, MODERATOR)
- `description`: Description of role permissions and responsibilities

**Relationships:**
- One Role can be assigned to many Users (Many-to-Many through UserRole)

### UserRole Entity

The UserRole entity is a junction table for the Many-to-Many relationship between User and Role.

**Attributes:**
- `id` (Primary Key): Unique identifier
- `userId` (Foreign Key): References User.id
- `roleId` (Foreign Key): References Role.id
- `assignedAt`: Timestamp when role was assigned

## Database Naming Conventions

- Table names: snake_case (e.g., `user_role`)
- Column names: camelCase in Java entities, snake_case in database
- Primary keys: `id`
- Foreign keys: `{entity}_id` (e.g., `user_id`, `role_id`)
- Timestamps: Use `TIMESTAMP` type with default values

## JPA Configuration

The project uses Spring Data JPA with Hibernate as the JPA provider. Configuration in `application.properties`:

```properties
# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/ccssaa_db
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

## Future Schema Extensions

The following entities may be added in future iterations:

1. **Profile Entity**: Extended user profile information
2. **AuditLog Entity**: Track user actions and system events
3. **Permission Entity**: Fine-grained permissions for role-based access control
4. **Session Entity**: Manage user sessions
5. **PasswordReset Entity**: Handle password reset requests

## Database Indexes

Recommended indexes for optimal performance:

- `User.username` - UNIQUE INDEX
- `User.email` - UNIQUE INDEX
- `User.enabled` - INDEX
- `Role.name` - UNIQUE INDEX
- `UserRole.userId` - INDEX
- `UserRole.roleId` - INDEX

## Database Migration

For production environments, consider using a database migration tool such as:
- **Flyway** or **Liquibase** for version-controlled schema changes
- Manual DDL scripts for critical updates

## Relationships Summary

| Entity 1 | Relationship | Entity 2 | Type |
|----------|--------------|----------|------|
| User | has many | Role | Many-to-Many |
| User | has many | UserRole | One-to-Many |
| Role | has many | UserRole | One-to-Many |

## Notes

- All entities include audit fields (`createdAt`, `updatedAt`)
- Password fields are encrypted using BCrypt
- Foreign key constraints ensure referential integrity
- Soft delete pattern can be implemented using a `deletedAt` timestamp field
