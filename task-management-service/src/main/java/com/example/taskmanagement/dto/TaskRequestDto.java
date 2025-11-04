//package com.example.taskmanagement.dto;
//
//public class TaskRequestDto {
//
//}



package com.example.taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDto {

    @NotBlank(message = "Task title cannot be empty")
    @Size(min = 3, max = 100, message = "Task title must be between 3 and 100 characters")
    private String title;

    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // For "yyyy-MM-dd" format
    private LocalDate dueDate;

    private Boolean completed; // Can be null for creation, will default in service

    private Long categoryId; // To link a task to a category
}