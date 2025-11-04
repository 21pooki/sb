//package com.example.taskmanagement.model;
//
//public class Task {
//
//}


package com.example.taskmanagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(nullable = false)
    private Boolean completed = false; // Default to not completed

    @ManyToOne(fetch = FetchType.EAGER) // Fetch category eagerly for simplicity
    @JoinColumn(name = "category_id")
    @JsonBackReference // Prevents infinite recursion when serializing Task -> Category -> Tasks...
    private Category category;
}