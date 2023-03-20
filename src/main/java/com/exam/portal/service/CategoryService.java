package com.exam.portal.service;

import org.springframework.data.domain.Page;

import com.exam.portal.entity.Category;

public interface CategoryService {
	
	void createCategory(Category category) throws Exception;
	
	Page<Category> getAllCategories(int pageNumber, int pageSize, String sortBy, String sortDirection);
	
	Category getCategoryById(int id);
	
	Category getCategoryByName(String name);
	
	void updateCategory(Category category);
	
	void deleteCategory(int id);

}
