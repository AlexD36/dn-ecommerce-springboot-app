# E-Commerce Backend with Spring Boot

![Java](https://img.shields.io/badge/Java-17+-brightgreen) 
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen) 
![H2 Database](https://img.shields.io/badge/Database-H2-blue) 
![Maven](https://img.shields.io/badge/Build-Maven-orange)

## **Overview**

This project is a backend system for an e-commerce application, built using **Java** and **Spring Boot**. It provides RESTful APIs for managing products, users, orders, and shopping carts. The backend is designed to be scalable, modular, and secure, following best practices for web application development.

---

## **Features**

### **User Management**
- User registration and authentication (JWT-based).
- Role-based access control (Admin and Customer).
- CRUD operations for user profiles.

### **Product Management**
- CRUD operations for products.
- Category-based product organization.
- Product listing with pagination and filtering.

### **Order Management**
- Place, view, and manage orders.
- Order history for users.
- Status updates for orders (e.g., pending, shipped, delivered).

### **Shopping Cart**
- Add, update, and remove items from the cart.
- Calculate cart totals dynamically.

### **Category Management**
- Organize products by categories.
- Manage categories via admin panel.

---

## **Technologies Used**

### **Backend Frameworks**
- **Spring Boot**: REST API development and application logic.
- **Spring Security**: For authentication and authorization.

### **Database**
- **H2 Database**: Lightweight in-memory database for rapid development and testing.
- **JPA/Hibernate**: ORM for database operations.

### **Tools & Build**
- **Maven**: Dependency management and build automation.
- **Lombok**: Reduce boilerplate code.
- **Swagger**: API documentation.

### **Testing**
- **JUnit**: Unit testing.
- - **Swagger**
  - **Postman**: Manual API testing.


---

## **Project Structure**

```plaintext
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── dn/
│   │           └── shop/
│   │               ├── config/              # Configuration classes
│   │               │   ├── SecurityConfig   # Security configuration
│   │               │   └── SwaggerConfig    # API documentation config
│   │               ├── controller/          # REST API controllers
│   │               │   ├── CategoryController
│   │               │   ├── OrderController
│   │               │   ├── ProductController
│   │               │   ├── TestController
│   │               │   └── UserController
│   │               ├── model/               # Data models
│   │               │   ├── dto/             # Data Transfer Objects
│   │               │   │   ├── cart/        # Cart-related DTOs
│   │               │   │   ├── category/    # Category-related DTOs
│   │               │   │   ├── order/       # Order-related DTOs
│   │               │   │   ├── product/     # Product-related DTOs
│   │               │   │   └── user/        # User-related DTOs
│   │               │   └── entity/          # JPA entities
│   │               ├── repository/          # Data access layer
│   │               ├── service/             # Business logic
│   │               ├── util/                # Utility classes
│   │               └── ShopApplication.java # Main application class
│   └── resources/
│       └── application.properties          # Application configuration
└── test/
    └── java/
        └── com/
            └── dn/
                └── shop/
                    └── ShopApplicationTests.java
```

---

## **API Documentation**

### **User Management**
- **PUT** `/api/users/{email}` - Update user's email address
- **DELETE** `/api/users/{email}` - Delete user account by email
- **POST** `/api/users/register` - Register new user account
- **POST** `/api/users/login` - Authenticate user and receive JWT token
- **POST** `/api/users/addCart` - Add a new cart to user's account
- **GET** `/api/users` - Retrieve list of all users (admin only)
- **GET** `/api/users/{id}` - Get user details by ID

### **Cart Management**
(Included in User Controller)
- **POST** `/api/users/{userId}/cart` - Add product to user's cart
- **DELETE** `/api/users/{userId}/cart/{productId}` - Remove product from cart

### **Product Management**
- **GET** `/api/v1/products` - Get all products (with pagination)
- **GET** `/api/v1/products/{id}` - Get product by ID
- **POST** `/api/v1/products` - Create new product
- **PUT** `/api/v1/products/{id}` - Update existing product
- **DELETE** `/api/v1/products/{id}` - Delete product

### **Order Management**
- **POST** `/api/orders` - Create a new order
- **GET** `/api/orders/user/{userId}` - Get orders by user
- **PUT** `/api/orders/{orderId}/status` - Update order status

### **Category Management**
- **GET** `/api/categories` - Get all categories
- **POST** `/api/categories` - Create new category

### **Testing Endpoint**
- **GET** `/test` - Test endpoint returning "Test431"

All endpoints are documented with Swagger UI, accessible at:
`http://localhost:8080/swagger-ui.html`

API documentation is available at:
`http://localhost:8080/api-docs`

---

## **Setup Instructions**

### **Prerequisites**
1. Install **Java 17** or higher.
2. Install **Maven** for build management.

### **Clone the Repository**

git clone https://github.com/AlexD36/dn-ecommerce-springboot-app

cd dn-ecommerce-springboot-app




### **Run the Application**
1. Build the project:
   ```bash
   mvn clean install
   ```
2. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### **Test the APIs**
1. Open your browser and navigate to:
   - **Swagger UI**: `http://localhost:8080/swagger-ui/index.html`.
2. Use **Postman** or Swagger to interact with the APIs.

---

## **Contributing**

### **Guidelines**
- Fork the repository and create a new branch.
- Follow clean code practices and write unit tests for new features.
- Ensure that all tests pass before creating a pull request.

---
