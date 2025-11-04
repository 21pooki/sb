package com.example.stdmanagement_.service;


import com.example.stdmanagement_.model.Student;
import com.example.stdmanagement_.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Indicates that this class is a "Service" component
public class StudentService {

    private final StudentRepository studentRepository;

    // Constructor injection for StudentRepository
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student studentDetails) {
        Optional<Student> studentOptional = studentRepository.findById(id);
        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            student.setFirstName(studentDetails.getFirstName());
            student.setLastName(studentDetails.getLastName());
            student.setEmail(studentDetails.getEmail());
            return studentRepository.save(student);
        }
        return null; // Or throw a specific exception if needed, keeping it simple for now
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}