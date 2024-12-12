
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
src/main/java/com/example/ecommerce/
├── controller/        # REST controllers for handling API requests.
├── dto/               # Data Transfer Objects for data input/output.
│   ├── user/          # User-related DTOs.
│   ├── product/       # Product-related DTOs.
│   ├── order/         # Order-related DTOs.
│   ├── cart/          # Shopping cart DTOs.
│   ├── category/      # Category DTOs.
├── entity/            # JPA entities mapped to database tables.
├── exception/         # Custom exception handling.
├── repository/        # Interfaces for database interactions.
├── service/           # Business logic services.
└── utils/             # Utility classes (e.g., JWT handling, mappers).
```

---

## **API Documentation**

### **Authentication**
- **POST** `/api/auth/register` - Register a new user.
- **POST** `/api/auth/login` - Authenticate and retrieve a JWT.

### **Product Management**
- **GET** `/api/products` - Fetch all products (with pagination).
- **GET** `/api/products/{id}` - Fetch a specific product.
- **POST** `/api/products` - Create a new product (Admin only).
- **PUT** `/api/products/{id}` - Update a product (Admin only).
- **DELETE** `/api/products/{id}` - Delete a product (Admin only).

### **Order Management**
- **POST** `/api/orders` - Place a new order.
- **GET** `/api/orders` - View order history (for the logged-in user).
- **PUT** `/api/orders/{id}/status` - Update order status (Admin only).

### **Cart Management**
- **GET** `/api/cart` - View items in the cart.
- **POST** `/api/cart` - Add an item to the cart.
- **PUT** `/api/cart/{id}` - Update an item's quantity in the cart.
- **DELETE** `/api/cart/{id}` - Remove an item from the cart.

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

## **Future Enhancements**
- Add product recommendations based on user behavior.
- Implement payment gateway integration.
- Develop a reporting module for admins.
- Enable email notifications for orders.

---

## **License**
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

## **Contact**
If you have any questions or suggestions, feel free to reach out:

- **Email**: your-email@example.com
- **GitHub**: [your-username](https://github.com/your-username)


### **Screenshots (Optional)**
Include screenshots of your Swagger UI, database structure, or example API requests and responses.

---

This README is comprehensive, professional, and ensures potential contributors or users can easily understand and work with your project. Let me know if you want specific adjustments or additional sections!
