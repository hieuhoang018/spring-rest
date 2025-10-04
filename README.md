# Spring REST API with JWT Authentication

A simple RESTful API built with **Spring Boot 3**, **Java 21**, **Spring Security (JWT)**, and **PostgreSQL**.  
The project demonstrates user registration/login with JWT-based authentication and CRUD operations for products using the Spring framework.

---

## 🚀 Features
- User registration and login
- JWT-based authentication and authorization
- Role-based access control (USER / ADMIN)
- Product CRUD API (secured)
- PostgreSQL persistence via Spring Data JPA
- Hibernate auto schema generation (`ddl-auto=update`)

---

## 🛠️ Tech Stack
- Java 21
- Spring Boot 3.3.x
- Spring Security 6
- Spring Data JPA (Hibernate)
- PostgreSQL 16+
- Maven
- JWT (jjwt 0.11.5)

---

## 📂 Project Structure
```
src/main/java/com/hieuhoang/springrest
 ├── auth
 │    ├── AuthController.java
 │    ├── JwtAuthFilter.java
 │    ├── JwtService.java
 │    └── SecurityConfig.java
 ├── product
 │    ├── Product.java
 │    ├── ProductController.java
 │    ├── ProductRepository.java
 │    └── ProductService.java
 ├── user
 │    ├── User.java
 │    ├── UserRepository.java
 │    └── UserService.java
 └── SpringRestApplication.java
```

---

## ⚙️ Setup

### 1. Clone the repo
```bash
git clone https://github.com/your-username/spring-rest-jwt.git
cd spring-rest-jwt
```

### 2. Configure PostgreSQL
- Create database:
```sql
CREATE DATABASE springrest;
```

- (Optional) Create a dedicated user:
```sql
CREATE USER appuser WITH PASSWORD 'appsecret';
ALTER DATABASE springrest OWNER TO appuser;
```

### 3. Update `src/main/resources/application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/springrest
spring.datasource.username=appuser
spring.datasource.password=appsecret

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
```

### 4. Run the app
```bash
mvn spring-boot:run
```

The app starts at:  
👉 `http://localhost:8080`

---

## ▶️ Using Maven Wrapper

This project includes the **Maven Wrapper** (`mvnw`, `mvnw.cmd`, and `.mvn/wrapper/maven-wrapper.properties`).  
This allows you to build and run the project without having Maven installed globally — the correct Maven version will be downloaded automatically.

### Commands

**On Linux / macOS**
```bash
./mvnw clean install
./mvnw spring-boot:run
```

**On Windows (PowerShell or CMD)**
```powershell
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

### Why use the Wrapper?
- Ensures all developers and CI/CD pipelines use the same Maven version.
- Makes onboarding easier — no need to install Maven separately.
- Recommended best practice for all modern Maven projects.

---

## 🔑 API Endpoints

### Authentication
- **Register**  
  `POST /api/auth/register`  
  ```json
  {
    "name": "Alice",
    "email": "alice@example.com",
    "password": "secret123"
  }
  ```

- **Login**  
  `POST /api/auth/login`  
  ```json
  {
    "email": "alice@example.com",
    "password": "secret123"
  }
  ```
  Response:
  ```json
  { "token": "eyJhbGciOiJIUzI1NiJ9..." }
  ```

Use the returned token in all protected endpoints:  
`Authorization: Bearer <token>`

---

### Products (Secured)
- **GET /api/products** → list products
- **GET /api/products/{id}** → get single product
- **POST /api/products** → create product
  ```json
  {
    "name": "Laptop",
    "price": 1500.0
  }
  ```
- **PUT /api/products/{id}** → update product
- **DELETE /api/products/{id}** → delete product

---

## 🧪 Testing with Postman
1. Register a new user.
2. Login to get a JWT token.
3. Add `Authorization: Bearer <token>` header to all product requests.

