# AuthService

AuthService is a Spring Boot-based authentication microservice that provides user registration, JWT-based authentication, and secure endpoint protection. It uses an in-memory H2 database by default and is easily extendable for production environments.

## Table of Contents

* [Features](#features)
* [Prerequisites](#prerequisites)
* [Installation](#installation)
* [Configuration](#configuration)
* [Running the Application](#running-the-application)
* [API Endpoints](#api-endpoints)
* [Project Structure](#project-structure)
* [Testing](#testing)
* [License](#license)

## Features

* User registration (sign up) with roles
* User authentication (sign in) with JWT token generation
* JWT-based request filtering via a custom filter
* Secure endpoints using Spring Security
* In-memory H2 database for quick start
* Easily configurable for other databases

## Prerequisites

* Java 17 or higher
* Maven 3.6+
* (Optional) Docker & Docker Compose for containerization

## Installation

1. Clone the repository:

   ```bash
   git clone <repository-url>
   cd AuthService
   ```
2. Build the project with Maven:

   ```bash
   mvn clean package
   ```

## Configuration

Application properties are defined in `src/main/resources/application.properties`:

```properties
spring.application.name=AuthService
server.port=8080

spring.datasource.url=jdbc:h2:mem:authdb
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.hibernate.ddl-auto=update

# JWT settings
jwt.secret=YourSecretKeyHere
jwt.expirationMs=3600000
```

* **server.port**: Port the service runs on (default: 8080)
* **spring.datasource.url**: Data source configuration (default: in-memory H2)
* **jwt.secret**: Secret key for signing JWT tokens
* **jwt.expirationMs**: Token expiration time in milliseconds

## Running the Application

```bash
# Using Maven
t mvn spring-boot:run

# Or run the packaged jar
java -jar target/AuthService-0.0.1-SNAPSHOT.jar
```

## API Endpoints

### Authentication

* **POST `/api/auth/signup`**
  Register a new user.
  **Request Body**:

  ```json
  {
    "username": "johndoe",
    "password": "password123",
    "role": ["ROLE_USER"]
  }
  ```

  **Response**: `200 OK` with confirmation message.

* **POST `/api/auth/signin`**
  Authenticate a user and receive a JWT.
  **Request Body**:

  ```json
  {
    "username": "johndoe",
    "password": "password123"
  }
  ```

  **Response**: `200 OK` with JWT token and user details.

### Check Service

* **GET `/check`**
  Simple health check endpoint. Requires valid JWT in `Authorization: Bearer <token>` header.
  **Response**: `200 OK` with message "If You are seeing this Text then it is WORKING".

## Project Structure

```
AuthService
├── src
│   ├── main
│   │   ├── java/com/AuthService
│   │   │   ├── config
│   │   │   │   ├── JwtAuthFilter.java
│   │   │   │   ├── JwtUtils.java
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controllers
│   │   │   │   ├── AuthController.java
│   │   │   │   └── CheckController.java
│   │   │   ├── dto
│   │   │   │   ├── LoginRequest.java
│   │   │   │   ├── SignupRequest.java
│   │   │   │   └── JwtResponse.java
│   │   │   ├── entities
│   │   │   │   ├── Role.java
│   │   │   │   └── User.java
│   │   │   ├── repository
│   │   │   │   └── UserRepository.java
│   │   │   ├── services
│   │   │   │   ├── AuthService.java
│   │   │   │   └── UserDetailsServiceImpl.java
│   │   │   └── AuthServiceApplication.java
│   │   └── resources
│   │       └── application.properties
│   └── test
│       └── AuthServiceApplicationTests.java
├── pom.xml
├── HELP.md
├── mvnw, mvnw.cmd, .mvn/
└── .gitignore
```

## Testing

Run unit tests:

```bash
mvn test
```
