package com.ecommerce.emarket.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Specialized version of @Component and ControllerAdvice annotation which marks
// a class as a source for RestAPis
// It intercpets exceptions thrown by any controller in our app
@RestControllerAdvice

public class GlobalExceptionHandler {

    // This is Global excpetion interceptor while the above only intercepts
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        Map<String, String> response = new HashMap<>();

        // In Spring Boot, when dealing with validation errors in the context of
        // handling exceptions, the method e.getBindingResult() is typically used when e
        // is an instance of MethodArgumentNotValidException or a similar exception
        // related to validation.

        // When you validate request data (e.g., using @Valid or @Validated annotations)
        // and the validation fails, Spring may throw a MethodArgumentNotValidException.
        // This exception contains details about the validation errors, and you can
        // retrieve this information using the getBindingResult() method.

        // getBindingResult(): This method returns a BindingResult object which contains
        // all validation errors.
        BindingResult bindingResults = e.getBindingResult();

        bindingResults.getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            response.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Resource not Found Exception HAndler
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        String message = e.getMessage();

        return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);
    }

    // API Exception Handler
    @ExceptionHandler(APIException.class)
    public ResponseEntity<String> handleAPIException(APIException e) {
        String message = e.getMessage();

        return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
    }
}
