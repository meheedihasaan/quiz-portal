package com.exam.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.exam.portal.entity.Category;
import com.exam.portal.exception.NotFoundException;
import com.exam.portal.repository.CategoryRepository;
import com.exam.portal.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public void createCategory(Category category) throws Exception {
		this.categoryRepository.save(category);
	}

	@Override
	public Page<Category> getCategories(int pageNumber, int pageSize, String sortBy, String sortDirection) {
		Sort sort = null;
		if(sortDirection.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		else if(sortDirection.equalsIgnoreCase("desc")) {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Category> categoryPage = this.categoryRepository.findAll(pageable);
		return categoryPage;
	}

	@Override
	public Category getCategoryById(int id) {
		Category category = this.categoryRepository.findById(id).orElseThrow(()-> new NotFoundException("Category not found with id "+id));
		return category;
	}
	
	@Override
	public Category getCategoryByName(String name) {
		Category category = this.categoryRepository.findByNameIgnoreCase(name);
		return category;
	}

	@Override
	public void updateCategory(Category category) {
		Category existingCategory = this.categoryRepository.findById(category.getId()).orElseThrow(()-> new NotFoundException("Category not found with id "+category.getId()));
		existingCategory.setName(category.getName());
		existingCategory.setDescription(category.getDescription());
		this.categoryRepository.save(existingCategory);
	}

	@Override
	public void deleteCategory(int id) {
		Category category = this.categoryRepository.findById(id).orElseThrow(()-> new NotFoundException("Category not found with id "+id));
		this.categoryRepository.delete(category);
	}

}
