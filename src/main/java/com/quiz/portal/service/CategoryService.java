package com.quiz.portal.service;

import com.quiz.portal.entity.Category;
import com.quiz.portal.exception.custom.NotFoundException;
import com.quiz.portal.repository.CategoryRepository;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void createCategory(Category category) {
        this.categoryRepository.save(category);
    }

    public Page<Category> getCategories(Pageable pageable) {
        return this.categoryRepository.findAll(pageable);
    }

    public List<Category> getCategoryList() {
        return this.categoryRepository.findAll();
    }

    public Category getCategoryById(UUID id) {
        return this.categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found."));
    }

    public Category getCategoryByName(String name) {
        return this.categoryRepository.findByNameIgnoreCase(name);
    }

    public void updateCategory(UUID id, Category category) {
        Category existingCategory =
                this.categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found."));
        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        this.categoryRepository.save(existingCategory);
    }

    public void deleteCategory(UUID id) {
        Category category =
                this.categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found."));
        this.categoryRepository.delete(category);
    }

    public long countCategories() {
        return this.categoryRepository.count();
    }
}
