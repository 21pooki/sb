//package com.example.employee.model;
//
//public class Employee {
//
//}




package com.example.employee.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal; // For precise salary representation

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eno; // Employee Number (primary key) - mapped from user's "Eno"
    private String ename; // Employee Name
    private String designation;
    private String deptName; // Department Name
    private BigDecimal salary; // Using BigDecimal for currency
}