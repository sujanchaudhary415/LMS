# 🎓 Learning Management System

A production-oriented **Learning Management System REST API** built with Java and Spring Boot. The project focuses on authentication, role-based authorization, JPA relationships, course management, enrollment, validation, and secure API design.

## ✨ Features

* 🔐 JWT authentication with HTTP-only cookies
* 👥 Multiple roles: `STUDENT`, `INSTRUCTOR`
* 📝 User registration and login
* 📚 Course management
* 📖 Lesson management
* 🎓 Student enrollment
* 🔒 Role-based authorization
* 👤 Resource ownership validation
* 📄 Pagination
* ⚠️ Global exception handling
* 🗺️ DTO mapping with MapStruct
* 📖 Swagger/OpenAPI documentation
* 🐳 Docker & Docker Compose
* 🗄️ PostgreSQL database

## 🛠️ Tech Stack

| Technology                  | Purpose                        |
| --------------------------- | ------------------------------ |
| Java 21                     | Programming language           |
| Spring Boot                 | Backend framework              |
| Spring Security             | Authentication & authorization |
| Spring Data JPA / Hibernate | ORM & database access          |
| PostgreSQL                  | Database                       |
| JWT                         | Authentication                 |
| MapStruct                   | DTO mapping                    |
| Swagger / OpenAPI           | API documentation              |
| Docker                      | Containerization               |
| Maven                       | Build tool                     |

## 🏗️ Architecture

```text
Client
  ↓
Controller
  ↓
Service
  ↓
Repository
  ↓
PostgreSQL
```

The application follows a layered architecture with DTOs, services, repositories, mappers, and centralized exception handling.

## 🔐 Security

* JWT access and refresh tokens
* HTTP-only cookies
* BCrypt password hashing
* Role-based authorization
* Secure resource ownership checks
* Database-backed refresh tokens

Access token: **15 minutes**
Refresh token: **7 days**

## 📚 Course & Enrollment

The system supports:

* Creating and managing courses
* Instructor ownership of courses
* Adding and managing lessons
* Student enrollment
* Preventing duplicate enrollments
* Restricting enrollment to users with the `STUDENT` role

A user can have both `STUDENT` and `INSTRUCTOR` roles while still being subject to role-specific business rules.

## 📖 API Documentation

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

## 🐳 Run with Docker

Build and start the application:

```bash
docker compose up --build
```

Stop the containers:

```bash
docker compose down
```

The application runs on:

```text
http://localhost:8080
```

## 📁 Project Structure

```text
src/main/java/com/example/demo
├── config
├── controller
├── dto
├── entity
├── exception
├── mapper
├── repository
├── security
├── service
└── util
```

## 🚀 Future Improvements

* React frontend
* Production deployment
* CI/CD pipeline
* Integration testing with Testcontainers
* Flyway database migrations
* Course search and filtering
* File/video content management

## 👨‍💻 Author

**Sujan Chaudhary**

Java / Spring Boot Backend Developer
