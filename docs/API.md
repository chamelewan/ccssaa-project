# API Documentation

## Overview

This document describes the RESTful API endpoints for the CCSSAA Project.

## Base URL

```
http://localhost:8080/api/v1
```

## Authentication

Most endpoints require authentication using JWT (JSON Web Token).

### Authentication Header

```
Authorization: Bearer {token}
```

## API Endpoints

### Authentication

#### Login

Authenticate user and receive JWT token.

**Endpoint**: `POST /api/v1/auth/login`

**Request Body**:
```json
{
  "username": "string",
  "password": "string"
}
```

**Response** (200 OK):
```json
{
  "status": "success",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "type": "Bearer",
    "expiresIn": 3600,
    "user": {
      "id": 1,
      "username": "john_doe",
      "email": "john@example.com",
      "roles": ["USER"]
    }
  }
}
```

**Errors**:
- `401 Unauthorized`: Invalid credentials
- `400 Bad Request`: Missing required fields

#### Register

Create a new user account.

**Endpoint**: `POST /api/v1/auth/register`

**Request Body**:
```json
{
  "username": "string",
  "email": "string",
  "password": "string",
  "firstName": "string",
  "lastName": "string"
}
```

**Response** (201 Created):
```json
{
  "status": "success",
  "data": {
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "createdAt": "2026-02-01T11:39:43Z"
  },
  "message": "User registered successfully"
}
```

**Errors**:
- `400 Bad Request`: Validation errors
- `409 Conflict`: Username or email already exists

#### Logout

Invalidate the current JWT token.

**Endpoint**: `POST /api/v1/auth/logout`

**Headers**: Requires Authentication

**Response** (200 OK):
```json
{
  "status": "success",
  "message": "Logged out successfully"
}
```

### Users

#### Get All Users

Retrieve a list of all users (Admin only).

**Endpoint**: `GET /api/v1/users`

**Headers**: Requires Authentication (Admin role)

**Query Parameters**:
- `page` (optional): Page number (default: 0)
- `size` (optional): Page size (default: 20)
- `sort` (optional): Sort field (default: id)
- `direction` (optional): Sort direction (ASC/DESC, default: ASC)

**Response** (200 OK):
```json
{
  "status": "success",
  "data": {
    "content": [
      {
        "id": 1,
        "username": "john_doe",
        "email": "john@example.com",
        "firstName": "John",
        "lastName": "Doe",
        "enabled": true,
        "roles": ["USER"],
        "createdAt": "2026-02-01T11:39:43Z",
        "updatedAt": "2026-02-01T11:39:43Z"
      }
    ],
    "page": 0,
    "size": 20,
    "totalElements": 1,
    "totalPages": 1
  }
}
```

**Errors**:
- `401 Unauthorized`: Not authenticated
- `403 Forbidden`: Insufficient permissions

#### Get User by ID

Retrieve a specific user by ID.

**Endpoint**: `GET /api/v1/users/{id}`

**Headers**: Requires Authentication

**Path Parameters**:
- `id`: User ID

**Response** (200 OK):
```json
{
  "status": "success",
  "data": {
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "enabled": true,
    "roles": ["USER"],
    "createdAt": "2026-02-01T11:39:43Z",
    "updatedAt": "2026-02-01T11:39:43Z"
  }
}
```

**Errors**:
- `404 Not Found`: User not found
- `401 Unauthorized`: Not authenticated

#### Update User

Update user information.

**Endpoint**: `PUT /api/v1/users/{id}`

**Headers**: Requires Authentication

**Path Parameters**:
- `id`: User ID

**Request Body**:
```json
{
  "firstName": "string",
  "lastName": "string",
  "email": "string"
}
```

**Response** (200 OK):
```json
{
  "status": "success",
  "data": {
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "enabled": true,
    "roles": ["USER"],
    "updatedAt": "2026-02-01T12:00:00Z"
  },
  "message": "User updated successfully"
}
```

**Errors**:
- `404 Not Found`: User not found
- `403 Forbidden`: Cannot update other users
- `400 Bad Request`: Validation errors

#### Delete User

Delete a user (Admin only).

**Endpoint**: `DELETE /api/v1/users/{id}`

**Headers**: Requires Authentication (Admin role)

**Path Parameters**:
- `id`: User ID

**Response** (200 OK):
```json
{
  "status": "success",
  "message": "User deleted successfully"
}
```

**Errors**:
- `404 Not Found`: User not found
- `403 Forbidden`: Insufficient permissions

#### Change Password

Change user password.

**Endpoint**: `POST /api/v1/users/{id}/change-password`

**Headers**: Requires Authentication

**Path Parameters**:
- `id`: User ID

**Request Body**:
```json
{
  "currentPassword": "string",
  "newPassword": "string"
}
```

**Response** (200 OK):
```json
{
  "status": "success",
  "message": "Password changed successfully"
}
```

**Errors**:
- `400 Bad Request`: Invalid current password
- `403 Forbidden`: Cannot change other users' passwords

### Roles

#### Get All Roles

Retrieve all available roles.

**Endpoint**: `GET /api/v1/roles`

**Headers**: Requires Authentication (Admin role)

**Response** (200 OK):
```json
{
  "status": "success",
  "data": [
    {
      "id": 1,
      "name": "ADMIN",
      "description": "Administrator role with full access"
    },
    {
      "id": 2,
      "name": "USER",
      "description": "Standard user role"
    }
  ]
}
```

#### Assign Role to User

Assign a role to a user (Admin only).

**Endpoint**: `POST /api/v1/users/{userId}/roles`

**Headers**: Requires Authentication (Admin role)

**Path Parameters**:
- `userId`: User ID

**Request Body**:
```json
{
  "roleId": 1
}
```

**Response** (200 OK):
```json
{
  "status": "success",
  "message": "Role assigned successfully"
}
```

**Errors**:
- `404 Not Found`: User or role not found
- `409 Conflict`: Role already assigned

#### Remove Role from User

Remove a role from a user (Admin only).

**Endpoint**: `DELETE /api/v1/users/{userId}/roles/{roleId}`

**Headers**: Requires Authentication (Admin role)

**Path Parameters**:
- `userId`: User ID
- `roleId`: Role ID

**Response** (200 OK):
```json
{
  "status": "success",
  "message": "Role removed successfully"
}
```

**Errors**:
- `404 Not Found`: User or role not found

## HTTP Status Codes

| Status Code | Description |
|-------------|-------------|
| 200 OK | Request successful |
| 201 Created | Resource created successfully |
| 400 Bad Request | Invalid request data |
| 401 Unauthorized | Authentication required |
| 403 Forbidden | Insufficient permissions |
| 404 Not Found | Resource not found |
| 409 Conflict | Resource conflict (e.g., duplicate) |
| 500 Internal Server Error | Server error |

## Error Response Format

All error responses follow this format:

```json
{
  "status": "error",
  "error": {
    "code": "ERROR_CODE",
    "message": "Human-readable error message",
    "details": "Additional error details (optional)",
    "timestamp": "2026-02-01T11:39:43Z"
  }
}
```

## Common Error Codes

| Error Code | Description |
|------------|-------------|
| `INVALID_CREDENTIALS` | Invalid username or password |
| `USER_NOT_FOUND` | User does not exist |
| `ROLE_NOT_FOUND` | Role does not exist |
| `UNAUTHORIZED` | Not authenticated |
| `FORBIDDEN` | Insufficient permissions |
| `VALIDATION_ERROR` | Request validation failed |
| `DUPLICATE_USERNAME` | Username already exists |
| `DUPLICATE_EMAIL` | Email already exists |
| `INTERNAL_ERROR` | Server error |

## Rate Limiting

API endpoints are rate-limited to prevent abuse:
- **Standard users**: 100 requests per minute
- **Admin users**: 1000 requests per minute

Rate limit headers:
```
X-RateLimit-Limit: 100
X-RateLimit-Remaining: 95
X-RateLimit-Reset: 1234567890
```

## Pagination

List endpoints support pagination with the following query parameters:

- `page`: Page number (0-indexed, default: 0)
- `size`: Number of items per page (default: 20, max: 100)
- `sort`: Sort field (default: id)
- `direction`: Sort direction (ASC or DESC, default: ASC)

Example:
```
GET /api/v1/users?page=0&size=20&sort=username&direction=ASC
```

## Validation Rules

### User Registration

- **username**: 3-50 characters, alphanumeric and underscore only
- **email**: Valid email format, max 100 characters
- **password**: Min 8 characters, must contain uppercase, lowercase, number, and special character
- **firstName**: 1-50 characters
- **lastName**: 1-50 characters

## CORS

Cross-Origin Resource Sharing (CORS) is enabled for the following origins:
- `http://localhost:3000` (Development)
- Production domain (to be configured)

## Versioning

The API uses URL versioning:
- Current version: `v1`
- Base URL: `/api/v1`

Future versions will be accessible at `/api/v2`, etc.

## Testing

You can test the API using tools like:
- **Postman**: Import the Postman collection (link TBD)
- **cURL**: Command-line HTTP client
- **Swagger UI**: Interactive API documentation (when implemented)

Example cURL request:
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"john_doe","password":"password123"}'
```
