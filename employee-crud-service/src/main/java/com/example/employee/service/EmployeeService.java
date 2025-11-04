//package com.example.employee.service;
//
//public class EmployeeService {
//
//}




package com.example.employee.service;

import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeByEno(Long eno) {
        return employeeRepository.findById(eno);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long eno, Employee employeeDetails) {
        Optional<Employee> employeeOptional = employeeRepository.findById(eno);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.setEname(employeeDetails.getEname());
            employee.setDesignation(employeeDetails.getDesignation());
            employee.setDeptName(employeeDetails.getDeptName());
            employee.setSalary(employeeDetails.getSalary());
            return employeeRepository.save(employee);
        }
        return null; // Or throw an exception for not found
    }

    public void deleteEmployee(Long eno) {
        employeeRepository.deleteById(eno);
    }
}