# 🎫 Ticket Management API

A robust REST API for ticket management system (Jira like) built with **Spring Boot**, **Spring Security**, and **JWT Authentication**. Features role-based access control, comprehensive user management, and complete API documentation.

![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green?style=for-the-badge&logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-13+-blue?style=for-the-badge&logo=postgresql)
![Docker](https://img.shields.io/badge/docker-257bd6?style=for-the-badge&logo=docker&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-Authentication-red?style=for-the-badge&logo=jsonwebtokens)
![Swagger](https://img.shields.io/badge/OpenAPI-3.0-yellow?style=for-the-badge&logo=swagger)

## 🚀 Features

### 🔐 **Authentication & Security**
- **JWT-based stateless authentication**
- **Role-based access control** (ADMIN/DEVELOPER)
- **Password encryption** with BCrypt
- **Token expiration management**
- **Secure endpoints** with Spring Security

### 👥 **User Management**
- User registration and authentication
- Role-based permissions (ADMIN can manage users)
- Default admin user initialization
- Password management and security

### 🎟️ **Ticket System**
- Complete ticket lifecycle management
- User assignment and tracking
- Relationship mapping between users and tickets

### 📚 **API Documentation**
- **Interactive Swagger UI**
- **OpenAPI 3.0 specification**
- **Comprehensive endpoint documentation**
- **Built-in API testing interface**

## 🛠️ Tech Stack

| Technology | Purpose |
|------------|---------|
| **Java 17+** | Core programming language |
| **Spring Boot 3.x** | Application framework |
| **Spring Security** | Authentication & authorization |
| **Spring Data JPA** | Database abstraction |
| **PostgreSQL** | Primary database |
| **JWT (JJWT)** | Token-based authentication |
| **OpenAPI/Swagger** | API documentation |
| **Maven** | Dependency management |

## 📋 Prerequisites

- **Java 17** or higher
- **Docker**
- **Maven 3.6+**
- **PostgreSQL 13+**
- **IDE** (IntelliJ IDEA, Eclipse, VS Code)

## ⚡ Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/matiasalek/Jira-Clone-Java-Spring-Boot.git
cd Jira-Clone-Java-Spring-Boot 
```

### 2. Docker and PostgreSQL Database Set up 
```bash
docker compose up
```

### 3. Create a .env file with credentials
```properties
JWT_SECRET=
JWT_EXPIRATION=
APP_ADMIN_USERNAME=
APP_ADMIN_EMAIL=
APP_ADMIN_PASSWORD=
```

### 3. Configure Application Properties
```properties
spring.application.name=jiraclone
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
server.port=8080

spring.jackson.deserialization.read-enums-using-to-string=true
spring.jackson.serialization.write-enums-using-to-string=true

# JWT Configuration
jwt.secret=${JWT_SECRET}
jwt.expiration=${JWT_EXPIRATION}

# Default User Configuration
app.admin.username=${APP_ADMIN_USERNAME}
app.admin.email=${APP_ADMIN_EMAIL}
app.admin.password=${APP_ADMIN_PASSWORD}
```

### 4. Run the Application
```bash
mvn clean install
mvn spring-boot:run
```

### 5. Access the API
- **API Base URL**: `http://localhost:8080/api`
- **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI Docs**: `http://localhost:8080/v3/api-docs`

## 🔧 API Endpoints

### 🔑 Authentication
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `POST` | `/api/auth/register` | Register new user | Public |
| `POST` | `/api/auth/login` | User login | Public |

### 👤 User Management
| Method | Endpoint                         | Description | Access |
|--------|----------------------------------|-------------|--------|
| `GET` | `/api/user`                      | Get all users | Authenticated |
| `GET` | `/api/user/{id}`                 | Get user by ID | Authenticated |
| `POST` | `/api/user`                      | Create new user | Authenticated |
| `PUT` | `/api/user/{id}`                 | Update user | Authenticated |
| `DELETE` | `/api/user/{id}`                 | Delete user | ADMIN only |
| `PUT` | `/api/user/{id}/change-password` | Change password | Authenticated |
| `POST` | `/api/user/{id}/assign-ticket`   | Assign ticket | Authenticated |

### 🎫 Ticket Management
| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `GET` | `/api/ticket` | Get all tickets | Authenticated |
| `GET` | `/api/ticket/{id}` | Get ticket by ID | Authenticated |
| `POST` | `/api/ticket` | Create new ticket | Authenticated |
| `PUT` | `/api/ticket/{id}` | Update ticket | Authenticated |
| `DELETE` | `/api/ticket/{id}` | Delete ticket | ADMIN only |

## 🔐 Authentication Flow

### Registration
```json
POST /api/auth/register
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "securePassword123"
}
```

### Login
```json
POST /api/auth/login
{
  "username": "john_doe",
  "password": "securePassword123"
}
```

### Response
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "type": "Bearer",
  "username": "john_doe",
  "role": "DEVELOPER",
  "userId": 1
}
```

### Using the Token
```bash
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```

## 🏗️ Project Structure

```
src/main/java/com/matiasalek.jiraclone/
├── config/             # Configuration classes
│   ├── SecurityConfig.java
│   └── OpenApiConfig.java
├── controller/         # REST controllers
│   ├── AuthController.java
│   ├── UserController.java
│   └── TicketController.java
├── dto/               # Data Transfer Objects
│   ├── request/
│   └── response/
├── entity/            # JPA entities
│   ├── User.java
│   └── Ticket.java
├── enums/            # ENUMs 
├── repository/       # Data access layer
├── service/          # Business logic
├── security/         # Security components
│   ├── JwtUtil.java
│   ├── JwtRequestFilter.java
│   ├── CustomUserDetailsService.java
│   └── JwtAuthenticationEntryPoint.java
└── exception/            # Exception classes
```

## 🔒 Security Features

### JWT Implementation
- **Stateless authentication** using JSON Web Tokens
- **Token payload** includes user ID, username, and role
- **Configurable expiration** time (default: 24 hours)
- **Secure token validation** on every request

### Role-Based Access Control
- **DEVELOPER**: Default role for new registrations
- **ADMIN**: Can manage users and perform administrative tasks
- **Hierarchical permissions** with proper authorization checks

### Password Security
- **BCrypt hashing** for password storage
- **Password change** functionality with validation

## 📖 API Documentation

### Swagger UI Features
- **Interactive API testing** directly from the browser
- **Comprehensive endpoint documentation**
- **Request/response schema definitions**
- **Built-in JWT authentication** for testing protected endpoints

### Using Swagger UI
1. Navigate to `http://localhost:8080/swagger-ui/index.html`
2. Enter: `Bearer YOUR_JWT_TOKEN`
3. Test any endpoint directly from the interface

## 🧪 Testing the API

### Using cURL
```bash
# Register a new user
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","email":"test@example.com","password":"password123"}'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123"}'

# Access protected endpoint
curl -X GET http://localhost:8080/api/user \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Default Admin User
- **Username**: `admin`
- **Password**: `admin123`
- **Role**: `ADMIN`

---

> 📧 **Contact**: [matias.aleksandrowicz@gmail.com](mailto:matias.aleksandrowicz@gmail.com)  
> 🐙 **GitHub**: [github.com/matiasalek](https://github.com/matiasalek)  
> 💼 **LinkedIn**: [linkedin.com/in/matias-aleksandrowicz](https://www.linkedin.com/in/matias-aleksandrowicz/)