# E-commerce API

This is a Spring Boot RESTful API for an e-commerce application, providing endpoints to manage products, orders, users, and payments. The API is designed to be a backend solution, providing functionality for user authentication, product catalog management, and order processing.

## Features

- **User Authentication & Authorization**: Basic and JWT-based authentication to secure endpoints.
- **Product Management**: CRUD operations for products.
- **Order Management**: Managing customer orders, including creating, updating, and viewing orders.
- **Cart Functionality**: Adding, updating, and removing items from the cart.
- **Payment Processing**: Integration with payment gateways.
- **User Profile Management**: Update and retrieve user profiles.
- **Admin Panel**: Endpoint access for admins to manage products, orders, and users.

## Technologies Used

- **Java** - Programming language.
- **Spring Boot** - Framework for building the API.
- **Spring Security** - For authentication and authorization.
- **Spring Data JPA** - For data persistence.
- **Hibernate** - ORM for managing relational database.
- **MySQL/PostgreSQL** - Database (replace with your DB choice).
- **Maven** - Build automation tool.

## Prerequisites

- Java 17 or higher
- Maven 3.8+
- MySQL or PostgreSQL database

## Getting Started

1. **Clone the repository**:

   ```bash
   git clone https://github.com/yourusername/ecommerce-api.git
   cd ecommerce-api
   ```

2. ** Configure the database **

spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

3. ** Run the application **

`mvn spring-boot:run`
