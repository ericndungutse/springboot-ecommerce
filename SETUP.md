# Setup Guide

This guide will help you set up the Spring Boot E-commerce API on your local development environment.

## Table of Contents
1. [Prerequisites](#prerequisites)
2. [Installation](#installation)
3. [Database Configuration](#database-configuration)
4. [Application Configuration](#application-configuration)
5. [Running the Application](#running-the-application)
6. [Initial Data Setup](#initial-data-setup)
7. [Troubleshooting](#troubleshooting)

## Prerequisites

Before you begin, ensure you have the following installed on your system:

### Required Software

| Software | Version | Download Link |
|----------|---------|---------------|
| **Java JDK** | 17 or higher | [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/) |
| **Maven** | 3.8+ | [Apache Maven](https://maven.apache.org/download.cgi) |
| **Git** | Latest | [Git Downloads](https://git-scm.com/downloads) |

### Optional Software

| Software | Purpose | Download Link |
|----------|---------|---------------|
| **MySQL** | Production database | [MySQL Community Server](https://dev.mysql.com/downloads/mysql/) |
| **MySQL Workbench** | Database management | [MySQL Workbench](https://dev.mysql.com/downloads/workbench/) |
| **Postman** | API testing | [Postman](https://www.postman.com/downloads/) |
| **IDE** | Development | [IntelliJ IDEA](https://www.jetbrains.com/idea/) or [Eclipse](https://www.eclipse.org/downloads/) |

### Verify Installation

Check if all required software is properly installed:

```bash
# Check Java version
java -version

# Check Maven version
mvn -version

# Check Git version
git --version
```

Expected output should show versions 17+ for Java and 3.8+ for Maven.

## Installation

### 1. Clone the Repository

```bash
git clone https://github.com/ericndungutse/springboot-ecommerce.git
cd springboot-ecommerce
```

### 2. Verify Project Structure

```bash
ls -la
```

You should see:
```
├── README.MD
├── pom.xml
├── src/
├── mvnw
├── mvnw.cmd
└── .gitignore
```

## Database Configuration

You can choose between H2 (for development) or MySQL (for production).

### Option 1: H2 Database (Recommended for Development)

H2 is an in-memory database that's perfect for development and testing.

#### Create Configuration File

Create `src/main/resources/application.properties`:

```properties
# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:ecommerce_db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password

# Enable H2 Console for debugging
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# JWT Configuration
app.jwtSecret=mySecretKey
app.jwtExpirationMs=86400000

# Logging
logging.level.com.ecommerce.emarket=DEBUG
logging.level.org.springframework.security=DEBUG

# Server Configuration
server.port=8080
```

**Advantages of H2:**
- No installation required
- Automatic database creation
- Built-in web console
- Perfect for development and testing

**Access H2 Console:**
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:ecommerce_db`
- Username: `sa`
- Password: `password`

### Option 2: MySQL Database (For Production)

#### 2.1 Install MySQL

**Ubuntu/Debian:**
```bash
sudo apt update
sudo apt install mysql-server
sudo mysql_secure_installation
```

**Windows:**
Download and install MySQL Community Server from the official website.

**macOS:**
```bash
brew install mysql
brew services start mysql
```

#### 2.2 Create Database

Connect to MySQL and create the database:

```sql
mysql -u root -p

CREATE DATABASE ecommerce_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'ecommerce_user'@'localhost' IDENTIFIED BY 'secure_password';
GRANT ALL PRIVILEGES ON ecommerce_db.* TO 'ecommerce_user'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

#### 2.3 Configure Application

Create `src/main/resources/application.properties`:

```properties
# MySQL Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=ecommerce_user
spring.datasource.password=secure_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

# Connection Pool Configuration
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=20000

# JWT Configuration
app.jwtSecret=mySecretKey
app.jwtExpirationMs=86400000

# File Upload Configuration
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Logging
logging.level.com.ecommerce.emarket=INFO
logging.level.root=INFO

# Server Configuration
server.port=8080
```

## Application Configuration

### Environment-Specific Configuration

For different environments, you can create multiple configuration files:

#### Development (application-dev.properties)
```properties
# Development specific settings
spring.jpa.show-sql=true
logging.level.com.ecommerce.emarket=DEBUG
```

#### Production (application-prod.properties)
```properties
# Production specific settings
spring.jpa.show-sql=false
logging.level.com.ecommerce.emarket=WARN
app.jwtSecret=${JWT_SECRET:your-production-secret-key}
```

#### Test (application-test.properties)
```properties
# Test specific settings
spring.datasource.url=jdbc:h2:mem:testdb
spring.jpa.hibernate.ddl-auto=create-drop
```

### JWT Configuration

Generate a secure JWT secret for production:

```bash
# Generate a random 256-bit key
openssl rand -base64 32
```

Replace `mySecretKey` with the generated key in production.

## Running the Application

### 1. Build the Project

```bash
# Clean and compile
mvn clean compile

# Run tests (optional)
mvn test

# Package the application
mvn clean package
```

### 2. Run with Maven

```bash
# Run with default profile
mvn spring-boot:run

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### 3. Run as JAR

```bash
# Run the packaged JAR
java -jar target/emarket-0.0.1-SNAPSHOT.jar

# Run with specific profile
java -jar target/emarket-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

### 4. Verify Application Startup

Check the console output for:
```
Started EmarketApplication in X.XXX seconds
```

Access the application:
```bash
curl http://localhost:8080/api/public/categories
```

## Initial Data Setup

### Create Sample Data

You can create sample data using the API endpoints or by inserting directly into the database.

#### 1. Create Admin User

```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "email": "admin@example.com",
    "password": "Admin123!",
    "firstName": "Admin",
    "lastName": "User",
    "mobileNumber": "1234567890"
  }'
```

#### 2. Login as Admin

```bash
curl -X POST http://localhost:8080/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "password": "Admin123!"
  }'
```

Save the JWT token from the response.

#### 3. Create Categories

```bash
curl -X POST http://localhost:8080/api/admin/categories \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "categoryName": "Electronics"
  }'
```

#### 4. Add Products

```bash
curl -X POST http://localhost:8080/api/admin/categories/1/products \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "productName": "iPhone 13",
    "description": "Latest iPhone model",
    "price": 999.99,
    "discount": 10.0,
    "quantity": 50,
    "image": "iphone13.jpg"
  }'
```

## Troubleshooting

### Common Issues

#### 1. Port Already in Use

**Error**: `Port 8080 was already in use`

**Solution**:
```bash
# Find process using port 8080
lsof -i :8080

# Kill the process
kill -9 <PID>

# Or change port in application.properties
server.port=8081
```

#### 2. Database Connection Failed

**Error**: `Could not connect to database`

**Solutions**:
- Verify database is running
- Check connection parameters
- Ensure database user has proper permissions
- Test connection manually:

```bash
# For MySQL
mysql -u ecommerce_user -p -h localhost ecommerce_db
```

#### 3. Java Version Mismatch

**Error**: `UnsupportedClassVersionError`

**Solution**: Ensure you're using Java 17 or higher:
```bash
java -version
export JAVA_HOME=/path/to/java17
```

#### 4. Maven Build Fails

**Error**: Various compilation errors

**Solutions**:
```bash
# Clean Maven cache
mvn clean

# Update dependencies
mvn clean install -U

# Skip tests if needed
mvn clean install -DskipTests
```

#### 5. JWT Token Issues

**Error**: `Invalid JWT token`

**Solutions**:
- Check token expiration
- Verify JWT secret configuration
- Ensure correct header format: `Authorization: Bearer <token>`

### Debug Mode

Enable debug logging to troubleshoot issues:

```properties
# Add to application.properties
logging.level.com.ecommerce.emarket=DEBUG
logging.level.org.springframework.security=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

### Health Check Endpoints

Add Spring Boot Actuator for health monitoring:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Access health endpoints:
- `http://localhost:8080/actuator/health`
- `http://localhost:8080/actuator/info`

### Getting Help

If you encounter issues:

1. Check the application logs
2. Verify your configuration
3. Test with sample requests
4. Check the [GitHub Issues](https://github.com/ericndungutse/springboot-ecommerce/issues)
5. Create a new issue with:
   - Your configuration
   - Error messages
   - Steps to reproduce

## Next Steps

After successful setup:

1. Read the [API Documentation](API.md)
2. Test endpoints with Postman
3. Explore the codebase
4. Set up your IDE for development
5. Consider containerization with Docker