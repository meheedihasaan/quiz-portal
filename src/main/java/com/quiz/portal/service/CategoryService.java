package com.quiz.portal.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.quiz.portal.entity.Category;

public interface CategoryService {
	
	void createCategory(Category category);
	
	Page<Category> getCategories(Pageable pageable);
	
	List<Category> getCategoryList();
	
	Category getCategoryById(int id);
	
	Category getCategoryByName(String name);
	
	void updateCategory(int id, Category category);
	
	void deleteCategory(int id);
	
	long countCategories();

}
