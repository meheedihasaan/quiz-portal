package com.quiz.portal.service;

import com.quiz.portal.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    void createCategory(Category category);

    Page<Category> getCategories(Pageable pageable);

    List<Category> getCategoryList();

    Category getCategoryById(UUID id);

    Category getCategoryByName(String name);

    void updateCategory(UUID id, Category category);

    void deleteCategory(UUID id);

    long countCategories();

}
