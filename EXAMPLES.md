# API Usage Examples

This document provides practical examples of how to use the Spring Boot E-commerce API endpoints.

## Prerequisites

- Application running on `http://localhost:8080`
- `curl` or Postman for making HTTP requests
- Basic understanding of REST APIs and JSON

## Authentication Flow

### 1. User Registration

Register a new user account:

```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john.doe@example.com",
    "password": "SecurePass123!",
    "firstName": "John",
    "lastName": "Doe",
    "mobileNumber": "1234567890"
  }'
```

**Response:**
```json
{
  "message": "User registered successfully!",
  "status": "success"
}
```

### 2. User Login

Login with credentials to get JWT token:

```bash
curl -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "SecurePass123!"
  }'
```

**Response:**
```json
{
  "id": 1,
  "username": "john_doe",
  "jwt": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huX2RvZSIsImlhdCI6MTYxNjIzOTAyMiwiZXhwIjoxNjE2MzI1NDIyfQ.kP_WiNiVdz-h2Q1QfQQtY8dGfG9X9XQR2xGdgdKgJ0k",
  "roles": ["ROLE_USER"]
}
```

**Save the JWT token** - you'll need it for authenticated requests.

## Category Management

### 3. Get All Categories (Public)

```bash
curl -X GET "http://localhost:8080/api/public/categories?pageNumber=1&pageSize=10"
```

**Response:**
```json
{
  "content": [
    {
      "categoryId": 1,
      "categoryName": "Electronics"
    },
    {
      "categoryId": 2,
      "categoryName": "Clothing"
    }
  ],
  "pageNumber": 0,
  "pageSize": 10,
  "totalElements": 2,
  "totalPages": 1,
  "lastPage": true
}
```

### 4. Create Category (Admin Only)

First, register an admin user and login to get admin JWT token, then:

```bash
curl -X POST http://localhost:8080/api/admin/categories \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_ADMIN_JWT_TOKEN" \
  -d '{
    "categoryName": "Books"
  }'
```

**Response:**
```json
{
  "categoryId": 3,
  "categoryName": "Books"
}
```

## Product Management

### 5. Get All Products (Public)

```bash
curl -X GET "http://localhost:8080/api/public/products?pageNumber=1&pageSize=5&sortBy=productName&sortOrder=ASC"
```

**Response:**
```json
{
  "content": [
    {
      "productId": 1,
      "productName": "iPhone 13",
      "description": "Latest Apple smartphone with A15 Bionic chip",
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
  "pageSize": 5,
  "totalElements": 1,
  "totalPages": 1,
  "lastPage": true
}
```

### 6. Get Products by Category

```bash
curl -X GET http://localhost:8080/api/public/categories/1/products
```

### 7. Add Product to Category (Admin Only)

```bash
curl -X POST http://localhost:8080/api/admin/categories/1/products \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_ADMIN_JWT_TOKEN" \
  -d '{
    "productName": "Samsung Galaxy S21",
    "description": "Premium Android smartphone with excellent camera",
    "price": 799.99,
    "discount": 5.0,
    "quantity": 30,
    "image": "galaxy-s21.jpg"
  }'
```

**Response:**
```json
{
  "productId": 2,
  "productName": "Samsung Galaxy S21",
  "description": "Premium Android smartphone with excellent camera",
  "price": 799.99,
  "discount": 5.0,
  "specialPrice": 759.99,
  "quantity": 30,
  "image": "galaxy-s21.jpg",
  "category": {
    "categoryId": 1,
    "categoryName": "Electronics"
  }
}
```

## Shopping Cart Operations

### 8. Add Product to Cart

```bash
curl -X POST http://localhost:8080/api/carts/products/1/quantity/2 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**Response:**
```json
{
  "cartId": 1,
  "totalPrice": 1799.98,
  "products": [
    {
      "productId": 1,
      "productName": "iPhone 13",
      "quantity": 2,
      "price": 999.99,
      "discount": 10.0,
      "productPrice": 899.99
    }
  ]
}
```

### 9. Get User's Cart

```bash
curl -X GET http://localhost:8080/api/users/carts \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### 10. Update Cart Item Quantity

```bash
curl -X PUT http://localhost:8080/api/carts/products/1/quantity/3 \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### 11. Clear Cart

```bash
curl -X DELETE http://localhost:8080/api/carts \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

**Response:**
```json
{
  "message": "Cart cleared successfully",
  "status": "success"
}
```

## Address Management

### 12. Add User Address

```bash
curl -X POST http://localhost:8080/api/users/addresses \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "street": "123 Main Street",
    "buildingName": "Apartment Complex A",
    "city": "New York",
    "state": "NY",
    "country": "USA",
    "pincode": "10001"
  }'
```

**Response:**
```json
{
  "addressId": 1,
  "street": "123 Main Street",
  "buildingName": "Apartment Complex A",
  "city": "New York",
  "state": "NY",
  "country": "USA",
  "pincode": "10001"
}
```

### 13. Get User Addresses

```bash
curl -X GET http://localhost:8080/api/users/addresses \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Order Management

### 14. Create Order from Cart

First, add items to cart, then create an order:

```bash
curl -X POST http://localhost:8080/api/users/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "addressId": 1,
    "paymentMethod": "CREDIT_CARD",
    "pgName": "STRIPE",
    "pgPaymentId": "pi_1234567890abcdef",
    "pgStatus": "PAID",
    "pgResponseMessage": "Payment completed successfully"
  }'
```

**Response:**
```json
{
  "orderId": 1,
  "email": "john.doe@example.com",
  "orderDate": "2024-01-15T10:30:00",
  "totalAmount": 1799.98,
  "orderStatus": "PLACED",
  "orderItems": [
    {
      "productId": 1,
      "productName": "iPhone 13",
      "quantity": 2,
      "discount": 10.0,
      "orderedProductPrice": 899.99
    }
  ],
  "address": {
    "addressId": 1,
    "street": "123 Main Street",
    "city": "New York",
    "state": "NY",
    "country": "USA",
    "pincode": "10001"
  },
  "payment": {
    "paymentId": 1,
    "paymentMethod": "CREDIT_CARD",
    "pgName": "STRIPE",
    "pgPaymentId": "pi_1234567890abcdef",
    "pgStatus": "PAID"
  }
}
```

### 15. Get User Orders

```bash
curl -X GET http://localhost:8080/api/users/orders \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### 16. Get All Orders (Admin Only)

```bash
curl -X GET http://localhost:8080/api/admin/orders \
  -H "Authorization: Bearer YOUR_ADMIN_JWT_TOKEN"
```

## Complete Workflow Example

Here's a complete example of a typical e-commerce workflow:

### Step 1: Setup
```bash
# Set base URL
BASE_URL="http://localhost:8080/api"

# Register user
curl -X POST $BASE_URL/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "customer1",
    "email": "customer1@example.com",
    "password": "Password123!",
    "firstName": "Jane",
    "lastName": "Smith",
    "mobileNumber": "9876543210"
  }'

# Login and get token
TOKEN=$(curl -s -X POST $BASE_URL/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "username": "customer1",
    "password": "Password123!"
  }' | jq -r '.jwt')

echo "JWT Token: $TOKEN"
```

### Step 2: Browse Products
```bash
# Get categories
curl -X GET "$BASE_URL/public/categories"

# Get products in electronics category
curl -X GET "$BASE_URL/public/categories/1/products"
```

### Step 3: Shopping
```bash
# Add iPhone to cart (2 pieces)
curl -X POST "$BASE_URL/carts/products/1/quantity/2" \
  -H "Authorization: Bearer $TOKEN"

# Add Samsung phone to cart (1 piece)
curl -X POST "$BASE_URL/carts/products/2/quantity/1" \
  -H "Authorization: Bearer $TOKEN"

# View cart
curl -X GET "$BASE_URL/users/carts" \
  -H "Authorization: Bearer $TOKEN"
```

### Step 4: Add Address
```bash
# Add shipping address
curl -X POST "$BASE_URL/users/addresses" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "street": "456 Oak Avenue",
    "buildingName": "Green Valley Apartments",
    "city": "Los Angeles",
    "state": "CA",
    "country": "USA",
    "pincode": "90210"
  }'
```

### Step 5: Place Order
```bash
# Create order
curl -X POST "$BASE_URL/users/orders" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "addressId": 1,
    "paymentMethod": "DEBIT_CARD",
    "pgName": "PAYPAL",
    "pgPaymentId": "PAYID-ABCD1234",
    "pgStatus": "COMPLETED",
    "pgResponseMessage": "Payment successful"
  }'
```

## Error Handling Examples

### Authentication Error
```bash
# Request without token
curl -X GET http://localhost:8080/api/users/carts

# Response:
{
  "error": "Unauthorized",
  "message": "Full authentication is required to access this resource",
  "status": 401,
  "timestamp": "2024-01-15T10:30:00.000Z"
}
```

### Validation Error
```bash
# Invalid product data
curl -X POST http://localhost:8080/api/admin/categories/1/products \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_ADMIN_JWT_TOKEN" \
  -d '{
    "productName": "",
    "price": -100
  }'

# Response:
{
  "error": "Validation failed",
  "message": "Product name is required; Price must be positive",
  "status": 400,
  "timestamp": "2024-01-15T10:30:00.000Z"
}
```

## Testing with Postman

### Import Collection
You can create a Postman collection with these endpoints:

1. Create a new collection named "E-commerce API"
2. Add environment variables:
   - `base_url`: `http://localhost:8080/api`
   - `jwt_token`: (will be set after login)
3. Create requests for each endpoint
4. Use `{{jwt_token}}` in Authorization headers

### Environment Setup
```json
{
  "name": "E-commerce Local",
  "values": [
    {
      "key": "base_url",
      "value": "http://localhost:8080/api",
      "enabled": true
    },
    {
      "key": "jwt_token",
      "value": "",
      "enabled": true
    }
  ]
}
```

## Tips for API Usage

1. **Always include Content-Type header** for POST/PUT requests with JSON body
2. **Handle JWT token expiration** - tokens expire after 24 hours by default
3. **Use pagination** for large datasets to improve performance
4. **Check HTTP status codes** to understand response meanings
5. **Store JWT tokens securely** in production applications
6. **Validate input data** before sending requests
7. **Handle errors gracefully** in your client applications

## Next Steps

- Explore the [API Documentation](API.md) for detailed endpoint information
- Check the [Setup Guide](SETUP.md) for development environment setup
- Review the [Contributing Guide](CONTRIBUTING.md) if you want to contribute to the project