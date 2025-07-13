# Spring Boot E-commerce Restfull API

A comprehensive RESTful API for an e-commerce application built with Spring Boot. This backend solution provides complete functionality for user authentication, product catalog management, order processing, and cart operations.

## 🚀 Features

- **🔐 Authentication & Authorization**: JWT-based security with role-based access control (USER/ADMIN)
- **📦 Product Management**: Full CRUD operations for products with category support
- **🛒 Shopping Cart**: Add, update, remove items with quantity management
- **📋 Order Management**: Complete order lifecycle management
- **👤 User Management**: User registration, profile management, and address handling
- **🎯 Category Management**: Organize products with hierarchical categories
- **🔍 Search & Pagination**: Advanced product search with pagination support
- **📱 REST API**: Clean, RESTful endpoints with proper HTTP status codes

## 🛠️ Technology Stack

| Technology | Purpose |
|------------|---------|
| **Java 17** | Programming language |
| **Spring Boot 3.3.3** | Application framework |
| **Spring Security** | Authentication & authorization |
| **Spring Data JPA** | Data persistence layer |
| **Hibernate** | ORM for database operations |
| **JWT (JSON Web Tokens)** | Stateless authentication |
| **MySQL/H2** | Database (configurable) |
| **Maven** | Build automation & dependency management |
| **Lombok** | Reduce boilerplate code |
| **ModelMapper** | Object mapping |

## 📋 Prerequisites

Before running this application, make sure you have:

- **Java 17** or higher installed
- **Maven 3.8+** for build management
- **MySQL** database (or use embedded H2 for development)
- **Git** for version control

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/ericndungutse/springboot-ecommerce.git
cd springboot-ecommerce
```

### 2. Database Configuration

Create an `application.properties` file in `src/main/resources/`:

#### For MySQL (Production):
```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

# JWT Configuration
app.jwtSecret=mySecretKey
app.jwtExpirationMs=86400000

# Server Configuration
server.port=8080
```

#### For H2 (Development):
```properties
# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.h2.console.enabled=true

# JPA Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# JWT Configuration
app.jwtSecret=mySecretKey
app.jwtExpirationMs=86400000
```

### 3. Build and Run

```bash
# Build the project
mvn clean compile

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 4. Verify Installation

Check if the application is running:
```bash
curl http://localhost:8080/api/public/categories
```

## 📁 Project Structure

```
src/
├── main/
│   └── java/
│       └── com/ecommerce/emarket/
│           ├── config/          # Configuration classes
│           ├── controller/      # REST controllers
│           ├── exceptions/      # Custom exception handling
│           ├── model/          # JPA entity classes
│           ├── payload/        # DTOs and response classes
│           ├── repositories/   # Data access layer
│           ├── security/       # Security configuration
│           ├── service/        # Business logic layer
│           └── utils/          # Utility classes
└── test/                      # Test classes
```

## 🔐 Authentication

This API uses JWT (JSON Web Tokens) for authentication. To access protected endpoints:

1. **Register** a new user or **login** with existing credentials
2. Use the returned JWT token in the `Authorization` header: `Bearer <token>`

### Default Admin User

On first startup, you can create an admin user through the registration endpoint with admin privileges.

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api
```

### Authentication Endpoints

| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| POST | `/auth/signin` | User login | Public |
| POST | `/auth/signup` | User registration | Public |

### Category Endpoints

| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| GET | `/public/categories` | Get all categories | Public |
| POST | `/admin/categories` | Create category | Admin |
| PUT | `/admin/categories/{id}` | Update category | Admin |
| DELETE | `/admin/categories/{id}` | Delete category | Admin |

### Product Endpoints

| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| GET | `/public/products` | Get all products | Public |
| GET | `/public/categories/{categoryId}/products` | Get products by category | Public |
| POST | `/admin/categories/{categoryId}/products` | Add product | Admin |
| PUT | `/admin/products/{id}` | Update product | Admin |
| DELETE | `/admin/products/{id}` | Delete product | Admin |

### Cart Endpoints

| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| GET | `/users/carts` | Get user cart | User |
| POST | `/carts/products/{productId}/quantity/{quantity}` | Add to cart | User |
| PUT | `/carts/products/{productId}/quantity/{quantity}` | Update cart item | User |
| DELETE | `/carts` | Clear cart | User |

### Order Endpoints

| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| GET | `/users/orders` | Get user orders | User |
| POST | `/users/orders` | Create order | User |
| GET | `/admin/orders` | Get all orders | Admin |

### Address Endpoints

| Method | Endpoint | Description | Access |
|--------|----------|-------------|---------|
| GET | `/users/addresses` | Get user addresses | User |
| POST | `/users/addresses` | Add address | User |
| PUT | `/users/addresses/{id}` | Update address | User |
| DELETE | `/users/addresses/{id}` | Delete address | User |

## 🔧 Configuration

### Pagination

Default pagination settings can be modified in `AppConstants.java`:
- **Page Size**: 2 items per page
- **Sort Order**: Ascending
- **Sort Field**: ID field

### File Upload

The application supports file uploads for product images. Configure the upload directory in your `application.properties`.

## 🧪 Testing

Run tests with Maven:

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=EmarketApplicationTests
```

## 🚀 Deployment

### Using Docker (Coming Soon)

### Manual Deployment

1. Build the JAR file:
```bash
mvn clean package
```

2. Run the JAR:
```bash
java -jar target/emarket-0.0.1-SNAPSHOT.jar
```

## 🔍 API Usage Examples

### User Registration
```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com",
    "password": "password123",
    "role": ["user"]
  }'
```

### User Login
```bash
curl -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "password123"
  }'
```

### Get All Products
```bash
curl -X GET "http://localhost:8080/api/public/products?pageNumber=1&pageSize=10"
```

### Add Product to Cart (Requires Authentication)
```bash
curl -X POST http://localhost:8080/api/carts/products/1/quantity/2 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

If you encounter any problems or have questions:

1. Check the [Issues](https://github.com/ericndungutse/springboot-ecommerce/issues) page
2. Create a new issue with detailed description
3. Contact the maintainers

## 🔮 Future Enhancements

- [ ] Email notification system
- [ ] Payment gateway integration (Stripe, PayPal)
- [ ] Product reviews and ratings
- [ ] Inventory management
- [ ] Advanced search filters
- [ ] Docker containerization
- [ ] API rate limiting
- [ ] Caching with Redis
