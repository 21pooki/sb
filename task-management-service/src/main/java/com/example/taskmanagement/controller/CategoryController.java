package com.example.taskmanagement.controller;

import com.example.taskmanagement.dto.CategoryRequestDto;
import com.example.taskmanagement.model.Category;
import com.example.taskmanagement.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping // GET http://localhost:8084/api/categories
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}") // GET http://localhost:8084/api/categories/1
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping // POST http://localhost:8084/api/categories
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequestDto categoryDto) {
        try {
            Category createdCategory = categoryService.createCategory(categoryDto);
            return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT); // 409 Conflict for existing name
        }
    }

    @PutMapping("/{id}") // PUT http://localhost:8084/api/categories/1
    public ResponseEntity<?> updateCategory(@PathVariable("id") Long id, @Valid @RequestBody CategoryRequestDto categoryDto) {
        try {
            Category updatedCategory = categoryService.updateCategory(id, categoryDto);
            if (updatedCategory != null) {
                return ResponseEntity.ok(updatedCategory);
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT); // 409 Conflict for existing name
        }
    }

    @DeleteMapping("/{id}") // DELETE http://localhost:8084/api/categories/1
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") Long id) {
        if (categoryService.getCategoryById(id).isPresent()) {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // --- Global exception handler for validation errors within this controller ---
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