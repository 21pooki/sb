package com.example.employeedepartmentv2.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference; // Important for JSON serialization

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String position;

    // --- CRITICAL CONFIGURATION FOR RELATIONSHIP AND JSON SERIALIZATION ---
    @ManyToOne(fetch = FetchType.EAGER) // Load Department eagerly to avoid LazyInitializationException
    @JoinColumn(name = "department_id") // This is the foreign key column in the 'employee' table
    @JsonBackReference // Prevents infinite recursion when converting to JSON (Employee -> Department -> Employees -> ...)
    private Department department;
}