package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.TaskRequestDto;
import com.example.taskmanagement.model.Category;
import com.example.taskmanagement.model.Task;
import com.example.taskmanagement.repository.CategoryRepository;
import com.example.taskmanagement.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository; // To link categories

    public TaskService(TaskRepository taskRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(TaskRequestDto taskDto) {
        Task task = new Task();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setDueDate(taskDto.getDueDate());
        task.setCompleted(taskDto.getCompleted() != null ? taskDto.getCompleted() : false); // Default to false if not provided

        if (taskDto.getCategoryId() != null) {
            Optional<Category> categoryOptional = categoryRepository.findById(taskDto.getCategoryId());
            categoryOptional.ifPresent(task::setCategory); // Link category if found
        }
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, TaskRequestDto taskDto) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setTitle(taskDto.getTitle());
            task.setDescription(taskDto.getDescription());
            task.setDueDate(taskDto.getDueDate());
            task.setCompleted(taskDto.getCompleted() != null ? taskDto.getCompleted() : task.getCompleted()); // Update if provided

            if (taskDto.getCategoryId() != null) {
                Optional<Category> categoryOptional = categoryRepository.findById(taskDto.getCategoryId());
                categoryOptional.ifPresent(task::setCategory);
            } else {
                task.setCategory(null); // Allow unlinking category
            }
            return taskRepository.save(task);
        }
        return null; // Task not found
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    // --- Task-specific queries and actions ---

    public List<Task> getTasksByCategoryId(Long categoryId) {
        return taskRepository.findByCategoryId(categoryId);
    }

    public List<Task> getCompletedTasks() {
        return taskRepository.findByCompleted(true);
    }

    public List<Task> getPendingTasks() {
        return taskRepository.findByCompleted(false);
    }

    public List<Task> getOverdueTasks() {
        return taskRepository.findByDueDateBeforeAndCompletedFalse(LocalDate.now());
    }

    public Task markTaskAsCompleted(Long id, boolean completed) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setCompleted(completed);
            return taskRepository.save(task);
        }
        return null; // Task not found
    }
}