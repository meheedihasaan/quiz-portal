package com.quiz.portal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.portal.entity.QuizResult;
import com.quiz.portal.entity.User;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Integer> {
	
	Page<QuizResult> findAll(Pageable pageable);

	Page<QuizResult> findByUser(User user, Pageable pageable);
	
	long countByUser(User user);
	
}