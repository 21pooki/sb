package com.example.auth.controller;

import com.example.auth.dto.UserLoginDto;
import com.example.auth.dto.UserRegistrationDto;
import com.example.auth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register") // POST http://localhost:8083/api/auth/register
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegistrationDto registrationDto) {
        String result = userService.registerUser(registrationDto);
        if (result.contains("successful")) {
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login") // POST http://localhost:8083/api/auth/login
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserLoginDto loginDto) {
        String result = userService.loginUser(loginDto);
        if (result.contains("successful")) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.UNAUTHORIZED);
        }
    }

    // --- Global exception handler for validation errors ---
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

//
//
//. Register User (POST)
//Endpoint: POST http://localhost:8083/api/auth/register
//Request Header: Content-Type: application/json
//This operation takes a UserRegistrationDto as its request body.
//A) Successful Registration Request JSON:
//code
//JSON
//{
//    "username": "newuser123",
//    "password": "StrongPassword123!",
//    "email": "newuser@example.com"
//}
//Expected Successful Response:
//Status: 201 Created
//Body: "Registration successful!"
//B) Registration Request JSON triggering Validation Errors:
//Here's an example that violates several validation rules (empty fields, short password, invalid email):
//code
//JSON
//{
//    "username": "us",
//    "password": "123",
//    "email": "invalid-email"
//}
//Expected Error Response (Validation Errors):
//Status: 400 Bad Request
//Body (JSON):
//code
//JSON
//{
//    "username": "Username must be between 3 and 20 characters",
//    "password": "Password must be at least 6 characters long",
//    "email": "Invalid email format"
//}
//(Note: The exact order of fields in the error response might vary.)
//C) Registration Request JSON for Duplicate User/Email:
//If you try to register with a username or email that already exists:
//code
//JSON
//{
//    "username": "newuser123",  // Assuming this username is already taken
//    "password": "AnyValidPassword!",
//    "email": "another@example.com"
//}
//Expected Error Response (Duplicate Username):
//Status: 400 Bad Request
//Body: "Username already taken!"
//Or for duplicate email:
//code
//JSON
//{
//    "username": "uniqueuser",
//    "password": "AnyValidPassword!",
//    "email": "newuser@example.com" // Assuming this email is already registered
//}
//Expected Error Response (Duplicate Email):
//Status: 400 Bad Request
//Body: "Email already registered!"
//2. Login User (POST)
//Endpoint: POST http://localhost:8083/api/auth/login
//Request Header: Content-Type: application/json
//This operation takes a UserLoginDto as its request body.
//A) Successful Login Request JSON:
//code
//JSON
//{
//    "username": "newuser123",
//    "password": "StrongPassword123!"
//}
//Expected Successful Response:
//Status: 200 OK
//Body: "Login successful!"
//B) Login Request JSON triggering Validation Errors:
//Here's an example with empty fields:
//code
//JSON
//{
//    "username": "",
//    "password": ""
//}
//Expected Error Response (Validation Errors):
//Status: 400 Bad Request
//Body (JSON):
//code
//JSON
//{
//    "username": "Username cannot be empty",
//    "password": "Password cannot be empty"
//}
//C) Login Request JSON for Invalid Credentials:
//If the username or password does not match any registered user:
//code
//JSON
//{
//    "username": "nonexistentuser",
//    "password": "wrongpassword"
//}
//Expected Error Response (Invalid Credentials):
//Status: 401 Unauthorized
//Body: "Invalid username or password!"