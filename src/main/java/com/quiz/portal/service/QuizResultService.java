package com.quiz.portal.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.quiz.portal.entity.QuizResult;

public interface QuizResultService {

	QuizResult createQuizResult(QuizResult quizResult);
	
	Page<QuizResult> getQuizResults(Pageable pageable);
	
	QuizResult getQuizResultById(int id);
	
	Page<QuizResult> getQuizResultsByUser(int userId, Pageable pageable);
	
	long countAttemptsByUser(int userId);
	
	long countParticipants();
	
	double getAvgAccuracyByUser(int userId);
	
}