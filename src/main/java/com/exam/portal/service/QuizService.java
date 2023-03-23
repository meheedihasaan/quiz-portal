package com.exam.portal.service;

import org.springframework.data.domain.Page;

import com.exam.portal.entity.Quiz;

public interface QuizService {

	void createQuiz(Quiz quiz);
	
	Page<Quiz> getQuizzes(int pageNumber, int pageSize, String sortBy, String sortDirection);
	
	Quiz getQuizById(int id);
	
	void updateQuiz(int id, Quiz quiz);
	
	void deleteQuiz(int id);
	
}
