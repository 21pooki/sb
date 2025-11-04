package com.example.employeedepartmentv2.repository;

import com.example.employeedepartmentv2.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Custom method to find all employees belonging to a specific department by its ID
    List<Employee> findByDepartmentId(Long departmentId);
}