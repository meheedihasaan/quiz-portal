package com.exam.portal.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.exam.portal.entity.Category;

public interface CategoryService {
	
	void createCategory(Category category);
	
	Page<Category> getCategories(int pageNumber, int pageSize, String sortBy, String sortDirection);
	
	List<Category> getCategoryList();
	
	Category getCategoryById(int id);
	
	Category getCategoryByName(String name);
	
	void updateCategory(int id, Category category);
	
	void deleteCategory(int id);

}
