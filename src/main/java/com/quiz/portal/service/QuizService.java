package com.quiz.portal.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.quiz.portal.entity.Quiz;

public interface QuizService {

	void createQuiz(Quiz quiz);
	
	Page<Quiz> getQuizzes(int pageNumber, int pageSize, String sortBy, String sortDirection);
	
	Page<Quiz> getQuizzesByCategory(int categoryId, int pageNumber, int pageSize, String sortBy, String sortDirection);
	
	Page<Quiz> getPublishedQuizzes(int pageNumber, int pageSize, String sortBy, String sortDirection);
	
	List<Quiz> getPublishedQuizzes();
	
	Page<Quiz> getPublishedQuizzesByCategory(int categoryId, int pageNumber, int pageSize, String sortBy, String sortDirection);
	
	Quiz getQuizById(int id);
	
	void updateQuiz(int id, Quiz quiz);
	
	void deleteQuiz(int id);
	
	long countQuizzes();
	
	long countPublishedQuizzes();
	
}