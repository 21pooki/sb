package com.example.stdmanagement_.controller;



import com.example.stdmanagement_.model.Student;
import com.example.stdmanagement_.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Marks this class as a REST controller
@RequestMapping("/api/students") // Base path for all endpoints in this controller
public class StudentController {

    private final StudentService studentService;

    // Constructor injection for StudentService
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping // Handles GET requests to /api/students
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}") // Handles GET requests to /api/students/{id}
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok) // If student is present, return 200 OK with student
                      .orElseGet(() -> ResponseEntity.notFound().build()); // Otherwise, return 404 Not Found
    }

    @PostMapping // Handles POST requests to /api/students
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED); // Return 201 Created
    }

    @PutMapping("/{id}") // Handles PUT requests to /api/students/{id}
    public ResponseEntity<Student> updateStudent(@PathVariable("id") Long id, @RequestBody Student studentDetails) {
        Student updatedStudent = studentService.updateStudent(id, studentDetails);
        if (updatedStudent != null) {
            return ResponseEntity.ok(updatedStudent); // Return 200 OK with updated student
        }
        return ResponseEntity.notFound().build(); // Return 404 Not Found if student doesn't exist
    }

    @DeleteMapping("/{id}") // Handles DELETE requests to /api/students/{id}
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            studentService.deleteStudent(id);
            return ResponseEntity.noContent().build(); // Return 204 No Content
        }
        return ResponseEntity.notFound().build(); // Return 404 Not Found if student doesn't exist
    }
}