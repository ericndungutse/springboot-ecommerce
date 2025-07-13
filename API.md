# API Documentation

This document provides detailed information about all available endpoints in the Spring Boot E-commerce API.

## Base URL
```
http://localhost:8080/api
```

## Authentication

Most endpoints require authentication via JWT tokens. Include the token in the `Authorization` header:
```
Authorization: Bearer <your-jwt-token>
```

## Response Format

All API responses follow a consistent JSON format:

### Success Response
```json
{
  "data": {},
  "message": "Success message",
  "status": "success"
}
```

### Error Response
```json
{
  "error": "Error message",
  "status": "error",
  "timestamp": "2024-01-01T00:00:00.000Z"
}
```

## Endpoints

### 🔐 Authentication Endpoints

#### User Registration
- **URL**: `/auth/signup`
- **Method**: `POST`
- **Access**: Public
- **Description**: Register a new user account

**Request Body**:
```json
{
  "username": "string",
  "email": "string",
  "password": "string",
  "firstName": "string",
  "lastName": "string",
  "mobileNumber": "string"
}
```

**Response**:
```json
{
  "message": "User registered successfully!",
  "status": "success"
}
```

#### User Login
- **URL**: `/auth/signin`
- **Method**: `POST`
- **Access**: Public
- **Description**: Authenticate user and receive JWT token

**Request Body**:
```json
{
  "username": "string",
  "password": "string"
}
```

**Response**:
```json
{
  "id": 1,
  "username": "john_doe",
  "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "roles": ["ROLE_USER"]
}
```

### 📂 Category Endpoints

#### Get All Categories
- **URL**: `/public/categories`
- **Method**: `GET`
- **Access**: Public
- **Description**: Retrieve all product categories with pagination

**Query Parameters**:
- `pageNumber` (optional): Page number (default: 0)
- `pageSize` (optional): Items per page (default: 2)
- `sortBy` (optional): Sort field (default: categoryId)
- `sortOrder` (optional): Sort direction - ASC/DESC (default: ASC)

**Response**:
```json
{
  "content": [
    {
      "categoryId": 1,
      "categoryName": "Electronics",
      "products": []
    }
  ],
  "pageNumber": 0,
  "pageSize": 2,
  "totalElements": 10,
  "totalPages": 5,
  "lastPage": false
}
```

#### Create Category (Admin Only)
- **URL**: `/admin/categories`
- **Method**: `POST`
- **Access**: Admin
- **Description**: Create a new product category

**Request Body**:
```json
{
  "categoryName": "Electronics"
}
```

**Response**:
```json
{
  "categoryId": 1,
  "categoryName": "Electronics"
}
```

#### Update Category (Admin Only)
- **URL**: `/admin/categories/{categoryId}`
- **Method**: `PUT`
- **Access**: Admin
- **Description**: Update an existing category

**Path Parameters**:
- `categoryId`: Category ID to update

**Request Body**:
```json
{
  "categoryName": "Updated Electronics"
}
```

#### Delete Category (Admin Only)
- **URL**: `/admin/categories/{categoryId}`
- **Method**: `DELETE`
- **Access**: Admin
- **Description**: Delete a category

**Path Parameters**:
- `categoryId`: Category ID to delete

### 📦 Product Endpoints

#### Get All Products
- **URL**: `/public/products`
- **Method**: `GET`
- **Access**: Public
- **Description**: Retrieve all products with pagination and sorting

**Query Parameters**:
- `pageNumber` (optional): Page number (default: 0)
- `pageSize` (optional): Items per page (default: 2)
- `sortBy` (optional): Sort field (default: productId)
- `sortOrder` (optional): Sort direction - ASC/DESC (default: ASC)

**Response**:
```json
{
  "content": [
    {
      "productId": 1,
      "productName": "iPhone 13",
      "description": "Latest iPhone model",
      "price": 999.99,
      "discount": 10.0,
      "specialPrice": 899.99,
      "quantity": 50,
      "image": "iphone13.jpg",
      "category": {
        "categoryId": 1,
        "categoryName": "Electronics"
      }
    }
  ],
  "pageNumber": 0,
  "pageSize": 2,
  "totalElements": 100,
  "totalPages": 50,
  "lastPage": false
}
```

#### Get Products by Category
- **URL**: `/public/categories/{categoryId}/products`
- **Method**: `GET`
- **Access**: Public
- **Description**: Get all products belonging to a specific category

**Path Parameters**:
- `categoryId`: Category ID

#### Add Product (Admin Only)
- **URL**: `/admin/categories/{categoryId}/products`
- **Method**: `POST`
- **Access**: Admin
- **Description**: Add a new product to a category

**Path Parameters**:
- `categoryId`: Category ID to add product to

**Request Body**:
```json
{
  "productName": "Samsung Galaxy S21",
  "description": "Latest Samsung smartphone",
  "price": 899.99,
  "discount": 5.0,
  "quantity": 30,
  "image": "galaxy-s21.jpg"
}
```

#### Update Product (Admin Only)
- **URL**: `/admin/products/{productId}`
- **Method**: `PUT`
- **Access**: Admin
- **Description**: Update an existing product

**Path Parameters**:
- `productId`: Product ID to update

#### Delete Product (Admin Only)
- **URL**: `/admin/products/{productId}`
- **Method**: `DELETE`
- **Access**: Admin
- **Description**: Delete a product

**Path Parameters**:
- `productId`: Product ID to delete

#### Upload Product Image (Admin Only)
- **URL**: `/admin/products/{productId}/image`
- **Method**: `PUT`
- **Access**: Admin
- **Description**: Upload an image for a product

**Path Parameters**:
- `productId`: Product ID

**Request**: Multipart form data with image file

### 🛒 Cart Endpoints

#### Get User Cart
- **URL**: `/users/carts`
- **Method**: `GET`
- **Access**: User
- **Description**: Get current user's shopping cart

**Response**:
```json
{
  "cartId": 1,
  "totalPrice": 1299.98,
  "products": [
    {
      "productId": 1,
      "productName": "iPhone 13",
      "quantity": 2,
      "price": 899.99,
      "discount": 10.0,
      "productPrice": 799.99
    }
  ]
}
```

#### Add Product to Cart
- **URL**: `/carts/products/{productId}/quantity/{quantity}`
- **Method**: `POST`
- **Access**: User
- **Description**: Add a product to the user's cart

**Path Parameters**:
- `productId`: Product ID to add
- `quantity`: Quantity to add

**Response**: Returns updated cart information

#### Update Cart Item Quantity
- **URL**: `/carts/products/{productId}/quantity/{quantity}`
- **Method**: `PUT`
- **Access**: User
- **Description**: Update quantity of a product in cart

**Path Parameters**:
- `productId`: Product ID to update
- `quantity`: New quantity

#### Clear Cart
- **URL**: `/carts`
- **Method**: `DELETE`
- **Access**: User
- **Description**: Remove all items from user's cart

### 📋 Order Endpoints

#### Get User Orders
- **URL**: `/users/orders`
- **Method**: `GET`
- **Access**: User
- **Description**: Get all orders for the current user

**Response**:
```json
{
  "content": [
    {
      "orderId": 1,
      "email": "user@example.com",
      "orderDate": "2024-01-01T10:00:00",
      "totalAmount": 1299.98,
      "orderStatus": "CONFIRMED",
      "orderItems": [
        {
          "productId": 1,
          "productName": "iPhone 13",
          "quantity": 2,
          "discount": 10.0,
          "orderedProductPrice": 799.99
        }
      ],
      "address": {
        "street": "123 Main St",
        "city": "New York",
        "state": "NY",
        "country": "USA",
        "pincode": "10001"
      },
      "payment": {
        "paymentId": 1,
        "paymentMethod": "CREDIT_CARD"
      }
    }
  ]
}
```

#### Create Order
- **URL**: `/users/orders`
- **Method**: `POST`
- **Access**: User
- **Description**: Create a new order from cart items

**Request Body**:
```json
{
  "addressId": 1,
  "paymentMethod": "CREDIT_CARD",
  "pgName": "STRIPE",
  "pgPaymentId": "pi_1234567890",
  "pgStatus": "PAID",
  "pgResponseMessage": "Payment successful"
}
```

#### Get All Orders (Admin Only)
- **URL**: `/admin/orders`
- **Method**: `GET`
- **Access**: Admin
- **Description**: Get all orders in the system

#### Update Order Status (Admin Only)
- **URL**: `/admin/orders/{orderId}/status/{status}`
- **Method**: `PUT`
- **Access**: Admin
- **Description**: Update order status

**Path Parameters**:
- `orderId`: Order ID to update
- `status`: New status (PLACED, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)

### 🏠 Address Endpoints

#### Get User Addresses
- **URL**: `/users/addresses`
- **Method**: `GET`
- **Access**: User
- **Description**: Get all addresses for the current user

**Response**:
```json
{
  "content": [
    {
      "addressId": 1,
      "street": "123 Main St",
      "buildingName": "Apartment Complex",
      "city": "New York",
      "state": "NY",
      "country": "USA",
      "pincode": "10001"
    }
  ]
}
```

#### Add Address
- **URL**: `/users/addresses`
- **Method**: `POST`
- **Access**: User
- **Description**: Add a new address for the user

**Request Body**:
```json
{
  "street": "456 Oak St",
  "buildingName": "Office Building",
  "city": "Los Angeles",
  "state": "CA",
  "country": "USA",
  "pincode": "90210"
}
```

#### Update Address
- **URL**: `/users/addresses/{addressId}`
- **Method**: `PUT`
- **Access**: User
- **Description**: Update an existing address

**Path Parameters**:
- `addressId`: Address ID to update

#### Delete Address
- **URL**: `/users/addresses/{addressId}`
- **Method**: `DELETE`
- **Access**: User
- **Description**: Delete an address

**Path Parameters**:
- `addressId`: Address ID to delete

## Error Codes

| HTTP Status | Description |
|-------------|-------------|
| 200 | OK - Request successful |
| 201 | Created - Resource created successfully |
| 400 | Bad Request - Invalid request data |
| 401 | Unauthorized - Authentication required |
| 403 | Forbidden - Access denied |
| 404 | Not Found - Resource not found |
| 409 | Conflict - Resource already exists |
| 500 | Internal Server Error - Server error |

## Rate Limiting

Currently, there are no rate limits implemented. This is planned for future releases.

## Pagination

All list endpoints support pagination with the following parameters:
- `pageNumber`: Zero-based page number
- `pageSize`: Number of items per page
- `sortBy`: Field to sort by
- `sortOrder`: ASC or DESC

## Data Validation

All input data is validated using Bean Validation annotations. Common validation rules include:
- Required fields cannot be null or empty
- Email format validation
- Minimum/maximum length constraints
- Numeric range validation

## Security

- All admin endpoints require ADMIN role
- User endpoints require authentication
- Public endpoints are accessible without authentication
- JWT tokens expire after 24 hours (configurable)
- Passwords are encrypted using BCrypt