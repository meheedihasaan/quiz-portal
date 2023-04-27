package com.quiz.portal.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quiz.portal.entity.Category;
import com.quiz.portal.exception.custom.NotFoundException;
import com.quiz.portal.repository.CategoryRepository;
import com.quiz.portal.service.CategoryService;

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
		Page<Category> categoryPage = this.categoryRepository.findAll(pageable);
		return categoryPage;
	}
	
	@Override
	public List<Category> getCategoryList() {
		List<Category> categories = this.categoryRepository.findAll();
		return categories;
	}

	@Override
	public Category getCategoryById(int id) {
		Category category = this.categoryRepository.findById(id).orElseThrow(()-> new NotFoundException("Category not found."));
		return category;
	}
	
	@Override
	public Category getCategoryByName(String name) {
		Category category = this.categoryRepository.findByNameIgnoreCase(name);
		return category;
	}

	@Override
	public void updateCategory(int id, Category category) {
		Category existingCategory = this.categoryRepository.findById(id).orElseThrow(()-> new NotFoundException("Category not found."));
		existingCategory.setName(category.getName());
		existingCategory.setDescription(category.getDescription());
		this.categoryRepository.save(existingCategory);
	}

	@Override
	public void deleteCategory(int id) {
		Category category = this.categoryRepository.findById(id).orElseThrow(()-> new NotFoundException("Category not found."));
		this.categoryRepository.delete(category);
	}
	
	@Override
	public long countCategories() {
		long totalCategory = this.categoryRepository.count();
		return totalCategory;
	}

}
