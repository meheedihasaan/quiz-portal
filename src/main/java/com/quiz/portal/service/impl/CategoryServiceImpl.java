package com.quiz.portal.service.impl;

import com.quiz.portal.entity.Category;
import com.quiz.portal.exception.custom.NotFoundException;
import com.quiz.portal.repository.CategoryRepository;
import com.quiz.portal.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public void createCategory(Category category) {
        this.categoryRepository.save(category);
    }

    @Override
    public Page<Category> getCategories(Pageable pageable) {
        return this.categoryRepository.findAll(pageable);
    }

    @Override
    public List<Category> getCategoryList() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(UUID id) {
        return this.categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found."));
    }

    @Override
    public Category getCategoryByName(String name) {
        return this.categoryRepository.findByNameIgnoreCase(name);
    }

    @Override
    public void updateCategory(UUID id, Category category) {
        Category existingCategory = this.categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found."));
        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        this.categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(UUID id) {
        Category category = this.categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found."));
        this.categoryRepository.delete(category);
    }

    @Override
    public long countCategories() {
        return this.categoryRepository.count();
    }

}
