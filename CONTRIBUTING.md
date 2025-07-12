# Contributing Guide

Thank you for your interest in contributing to the Spring Boot E-commerce API! This guide will help you get started with contributing to the project.

## Table of Contents
1. [Code of Conduct](#code-of-conduct)
2. [Getting Started](#getting-started)
3. [Development Workflow](#development-workflow)
4. [Coding Standards](#coding-standards)
5. [Testing Guidelines](#testing-guidelines)
6. [Submitting Changes](#submitting-changes)
7. [Project Structure](#project-structure)

## Code of Conduct

This project adheres to a code of conduct. By participating, you are expected to uphold this code:

- Use welcoming and inclusive language
- Be respectful of differing viewpoints and experiences
- Gracefully accept constructive criticism
- Focus on what is best for the community
- Show empathy towards other community members

## Getting Started

### Prerequisites

Before you start contributing, make sure you have:

1. **Java 17** or higher installed
2. **Maven 3.8+** for build management
3. **Git** for version control
4. **IDE** (IntelliJ IDEA, Eclipse, or VS Code recommended)
5. Basic knowledge of Spring Boot, JPA, and REST APIs

### Fork and Clone

1. Fork the repository on GitHub
2. Clone your fork locally:
   ```bash
   git clone https://github.com/YOUR_USERNAME/springboot-ecommerce.git
   cd springboot-ecommerce
   ```

3. Add the original repository as upstream:
   ```bash
   git remote add upstream https://github.com/ericndungutse/springboot-ecommerce.git
   ```

### Setup Development Environment

1. Follow the [Setup Guide](SETUP.md) to configure your development environment
2. Build the project:
   ```bash
   mvn clean compile
   ```
3. Run tests to ensure everything works:
   ```bash
   mvn test
   ```

## Development Workflow

### 1. Create a Feature Branch

Always create a new branch for your work:

```bash
# Update your main branch
git checkout main
git pull upstream main

# Create a new feature branch
git checkout -b feature/your-feature-name
```

### 2. Make Changes

- Write clean, readable code
- Follow the existing code style
- Add appropriate tests
- Update documentation if needed

### 3. Commit Your Changes

Use clear, descriptive commit messages:

```bash
git add .
git commit -m "Add user authentication feature

- Implement JWT-based authentication
- Add login and registration endpoints
- Include proper error handling
- Add unit tests for auth service"
```

### 4. Push and Create Pull Request

```bash
git push origin feature/your-feature-name
```

Then create a pull request on GitHub.

## Coding Standards

### Java Code Style

Follow these conventions:

#### Naming Conventions
- **Classes**: PascalCase (e.g., `UserService`, `ProductController`)
- **Methods**: camelCase (e.g., `getUserById`, `createProduct`)
- **Variables**: camelCase (e.g., `userName`, `productList`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_RETRY_COUNT`)
- **Packages**: lowercase (e.g., `com.ecommerce.emarket.service`)

#### Code Organization
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    // Dependencies first (autowired)
    @Autowired
    private UserService userService;
    
    // Public methods
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        // Implementation
    }
    
    // Private helper methods
    private void validateUser(UserDTO user) {
        // Implementation
    }
}
```

#### Documentation
- Add JavaDoc for public methods and classes
- Use meaningful variable and method names
- Add inline comments for complex logic

```java
/**
 * Retrieves user information by ID
 * 
 * @param id the user ID
 * @return UserDTO containing user information
 * @throws UserNotFoundException if user is not found
 */
@GetMapping("/{id}")
public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
    // Implementation
}
```

### Spring Boot Best Practices

#### Controllers
- Keep controllers thin - delegate business logic to services
- Use proper HTTP status codes
- Validate input parameters
- Handle exceptions appropriately

```java
@RestController
@RequestMapping("/api/products")
@Validated
public class ProductController {
    
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(
            @Valid @RequestBody ProductDTO productDTO) {
        
        ProductDTO created = productService.createProduct(productDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
```

#### Services
- Use `@Transactional` for database operations
- Implement proper exception handling
- Keep business logic separate from data access

```java
@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    
    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        // Validation logic
        // Business logic
        // Data persistence
    }
}
```

#### DTOs and Entities
- Use DTOs for API communication
- Keep entities focused on data structure
- Use proper validation annotations

```java
public class ProductDTO {
    
    @NotBlank(message = "Product name is required")
    @Size(max = 100, message = "Product name cannot exceed 100 characters")
    private String productName;
    
    @DecimalMin(value = "0.0", message = "Price must be positive")
    private Double price;
    
    // Getters and setters
}
```

## Testing Guidelines

### Test Structure

Follow the AAA pattern (Arrange, Act, Assert):

```java
@Test
@DisplayName("Should create product successfully")
void shouldCreateProductSuccessfully() {
    // Arrange
    ProductDTO productDTO = new ProductDTO();
    productDTO.setProductName("Test Product");
    productDTO.setPrice(99.99);
    
    // Act
    ProductDTO result = productService.createProduct(productDTO);
    
    // Assert
    assertThat(result.getProductName()).isEqualTo("Test Product");
    assertThat(result.getPrice()).isEqualTo(99.99);
}
```

### Testing Types

#### Unit Tests
- Test individual components in isolation
- Mock external dependencies
- Focus on business logic

```java
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    
    @Mock
    private ProductRepository productRepository;
    
    @InjectMocks
    private ProductServiceImpl productService;
    
    @Test
    void shouldCreateProduct() {
        // Test implementation
    }
}
```

#### Integration Tests
- Test component interactions
- Use `@SpringBootTest` for full context
- Test actual database operations

```java
@SpringBootTest
@AutoTestDatabase(replace = AutoTestDatabase.Replace.NONE)
@Transactional
class ProductIntegrationTest {
    
    @Autowired
    private ProductService productService;
    
    @Test
    void shouldPersistProduct() {
        // Test implementation
    }
}
```

#### Controller Tests
- Test HTTP endpoints
- Mock service dependencies
- Verify request/response handling

```java
@WebMvcTest(ProductController.class)
class ProductControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ProductService productService;
    
    @Test
    void shouldCreateProduct() throws Exception {
        // Test implementation using MockMvc
    }
}
```

### Test Coverage

- Aim for at least 80% code coverage
- Ensure critical business logic is well-tested
- Don't focus solely on coverage percentage

```bash
# Run tests with coverage
mvn test jacoco:report
```

## Submitting Changes

### Pull Request Guidelines

1. **Title**: Use a clear, descriptive title
2. **Description**: Explain what changes you made and why
3. **Testing**: Describe how you tested your changes
4. **Documentation**: Update relevant documentation

### Pull Request Template

```markdown
## Description
Brief description of changes made.

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Testing
- [ ] Unit tests added/updated
- [ ] Integration tests added/updated
- [ ] Manual testing performed

## Checklist
- [ ] Code follows style guidelines
- [ ] Self-review completed
- [ ] Documentation updated
- [ ] Tests pass locally
```

### Review Process

1. **Automated Checks**: Ensure all CI checks pass
2. **Code Review**: Address reviewer feedback
3. **Testing**: Verify functionality works as expected
4. **Documentation**: Ensure docs are updated
5. **Merge**: Maintainer will merge when approved

## Project Structure

Understanding the codebase structure:

```
src/main/java/com/ecommerce/emarket/
├── config/          # Configuration classes
├── controller/      # REST API controllers
├── exceptions/      # Custom exception classes
├── model/          # JPA entity classes
├── payload/        # DTO classes
├── repositories/   # Data access repositories
├── security/       # Security configuration
├── service/        # Business logic services
└── utils/          # Utility classes
```

### Adding New Features

#### New Entity
1. Create entity class in `model/`
2. Create repository in `repositories/`
3. Create DTO in `payload/`
4. Create service interface and implementation
5. Create controller with REST endpoints
6. Add appropriate tests

#### New Endpoint
1. Add method to appropriate controller
2. Implement service logic if needed
3. Add validation
4. Update API documentation
5. Add tests

## Common Issues

### Import Organization
Use consistent import organization:

```java
// Java standard library
import java.util.List;
import java.util.Optional;

// Third-party libraries
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

// Project imports
import com.ecommerce.emarket.model.Product;
import com.ecommerce.emarket.service.ProductService;
```

### Exception Handling
Use proper exception handling:

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(
            UserNotFoundException ex) {
        // Handle exception
    }
}
```

## Getting Help

If you need help:

1. Check existing [issues](https://github.com/ericndungutse/springboot-ecommerce/issues)
2. Read the [API documentation](API.md)
3. Ask questions in discussions
4. Contact maintainers

## Recognition

Contributors will be recognized in:
- README contributors section
- Release notes
- Project documentation

Thank you for contributing to the Spring Boot E-commerce API!