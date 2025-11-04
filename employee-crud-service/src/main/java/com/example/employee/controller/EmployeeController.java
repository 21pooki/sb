//package com.example.employee.controller;
//
//public class EmployeeController {
//
//}





package com.example.employee.controller;

import com.example.employee.model.Employee;
import com.example.employee.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping // GET http://localhost:8082/api/employees
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{eno}") // GET http://localhost:8082/api/employees/1
    public ResponseEntity<Employee> getEmployeeByEno(@PathVariable("eno") Long eno) {
        Optional<Employee> employee = employeeService.getEmployeeByEno(eno);
        return employee.map(ResponseEntity::ok)
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping // POST http://localhost:8082/api/employees
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.createEmployee(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @PutMapping("/{eno}") // PUT http://localhost:8082/api/employees/1
    public ResponseEntity<Employee> updateEmployee(@PathVariable("eno") Long eno, @RequestBody Employee employeeDetails) {
        Employee updatedEmployee = employeeService.updateEmployee(eno, employeeDetails);
        if (updatedEmployee != null) {
            return ResponseEntity.ok(updatedEmployee);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{eno}") // DELETE http://localhost:8082/api/employees/1
    public ResponseEntity<Void> deleteEmployee(@PathVariable("eno") Long eno) {
        Optional<Employee> employee = employeeService.getEmployeeByEno(eno);
        if (employee.isPresent()) {
            employeeService.deleteEmployee(eno);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}