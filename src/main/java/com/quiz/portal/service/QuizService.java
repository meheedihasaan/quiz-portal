package com.quiz.portal.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.quiz.portal.entity.Quiz;

public interface QuizService {

	void createQuiz(Quiz quiz);
	
	Page<Quiz> getQuizzes(Pageable pageable);
	
	Page<Quiz> getQuizzesByCategory(int categoryId, Pageable pageable);
	
	Page<Quiz> getPublishedQuizzes(Pageable pageable);
	
	List<Quiz> getPublishedQuizzes();
	
	Page<Quiz> getPublishedQuizzesByCategory(int categoryId, Pageable pageable);
	
	Quiz getQuizById(int id);
	
	void updateQuiz(int id, Quiz quiz);
	
	void deleteQuiz(int id);
	
	long countQuizzes();
	
	long countPublishedQuizzes();
	
}
