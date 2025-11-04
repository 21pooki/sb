package com.example.taskmanagement.controller;

import com.example.taskmanagement.dto.TaskRequestDto;
import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.service.TaskService;
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
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping // GET http://localhost:8084/api/tasks
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}") // GET http://localhost:8084/api/tasks/1
    public ResponseEntity<Task> getTaskById(@PathVariable("id") Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping // POST http://localhost:8084/api/tasks
    public ResponseEntity<Task> createTask(@Valid @RequestBody TaskRequestDto taskDto) {
        Task createdTask = taskService.createTask(taskDto);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PutMapping("/{id}") // PUT http://localhost:8084/api/tasks/1
    public ResponseEntity<Task> updateTask(@PathVariable("id") Long id, @Valid @RequestBody TaskRequestDto taskDto) {
        Task updatedTask = taskService.updateTask(id, taskDto);
        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}") // DELETE http://localhost:8084/api/tasks/1
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id) {
        if (taskService.getTaskById(id).isPresent()) {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // --- Specific Task Filtering Endpoints ---

    @GetMapping("/by-category/{categoryId}") // GET http://localhost:8084/api/tasks/by-category/1
    public List<Task> getTasksByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return taskService.getTasksByCategoryId(categoryId);
    }

    @GetMapping("/completed") // GET http://localhost:8084/api/tasks/completed
    public List<Task> getCompletedTasks() {
        return taskService.getCompletedTasks();
    }

    @GetMapping("/pending") // GET http://localhost:8084/api/tasks/pending
    public List<Task> getPendingTasks() {
        return taskService.getPendingTasks();
    }

    @GetMapping("/overdue") // GET http://localhost:8084/api/tasks/overdue
    public List<Task> getOverdueTasks() {
        return taskService.getOverdueTasks();
    }

    // --- Task Status Update Endpoints ---

    @PatchMapping("/{id}/complete") // PATCH http://localhost:8084/api/tasks/1/complete
    public ResponseEntity<Task> markTaskAsComplete(@PathVariable("id") Long id) {
        Task updatedTask = taskService.markTaskAsCompleted(id, true);
        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/uncomplete") // PATCH http://localhost:8084/api/tasks/1/uncomplete
    public ResponseEntity<Task> markTaskAsUncomplete(@PathVariable("id") Long id) {
        Task updatedTask = taskService.markTaskAsCompleted(id, false);
        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask);
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