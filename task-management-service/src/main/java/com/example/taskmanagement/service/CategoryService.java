package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.CategoryRequestDto;
import com.example.taskmanagement.model.Category;
import com.example.taskmanagement.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category createCategory(CategoryRequestDto categoryDto) {
        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new IllegalArgumentException("Category with name '" + categoryDto.getName() + "' already exists.");
        }
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, CategoryRequestDto categoryDto) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            // Check if name is changed and if new name already exists
            if (!category.getName().equals(categoryDto.getName()) && categoryRepository.existsByName(categoryDto.getName())) {
                throw new IllegalArgumentException("Category with name '" + categoryDto.getName() + "' already exists.");
            }
            category.setName(categoryDto.getName());
            category.setDescription(categoryDto.getDescription());
            return categoryRepository.save(category);
        }
        return null; // Category not found
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}