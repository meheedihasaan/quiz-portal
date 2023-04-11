package com.quiz.portal.service;

import org.springframework.data.domain.Page;

import com.quiz.portal.entity.QuizResult;

public interface QuizResultService {

	QuizResult createQuizResult(QuizResult quizResult);
	
	Page<QuizResult> getQuizResults(int pageNumber, int pageSize, String sortBy, String sortDirection);
	
	QuizResult getQuizResultById(int id);
	
	Page<QuizResult> getQuizResultsByUser(int userId, int pageNumber, int pageSize, String sortBy, String sortDirection);
	
	long countAttemptsByUser(int userId);
	
	long countParticipants();
	
	double getAvgAccuracyByUser(int userId);
	
}