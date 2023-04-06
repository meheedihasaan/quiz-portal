package com.exam.portal.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.portal.entity.Category;
import com.exam.portal.entity.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer> {
	
	Page<Quiz> findAll(Pageable pageable);
	
	Page<Quiz> findByCategory(Category category, Pageable pageable);
	
	Page<Quiz> findByIsActive(boolean isActive, Pageable pageable);
	
	List<Quiz> findByIsActive(boolean isActive);
	
	Page<Quiz> findByCategoryAndIsActive(Category category, boolean isActive, Pageable pageable);
	
	long countByIsActive(boolean isActive);
	
}
