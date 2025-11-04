package com.example.stdmanagement_.repository;


import com.example.stdmanagement_.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Indicates that this interface is a "Repository"
public interface StudentRepository extends JpaRepository<Student, Long> {
    // JpaRepository provides methods like save(), findById(), findAll(), deleteById(), etc.
    // No need to implement them, Spring Data JPA does it for you.
}