package com.quiz.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.portal.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	Category findByNameIgnoreCase(String name);
	
}
