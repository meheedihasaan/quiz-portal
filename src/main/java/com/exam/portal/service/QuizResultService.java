package com.exam.portal.service;

import org.springframework.data.domain.Page;

import com.exam.portal.entity.QuizResult;

public interface QuizResultService {

	QuizResult createQuizResult(QuizResult quizResult);
	
	QuizResult getQuizResultById(int id);
	
	Page<QuizResult> getQuizResultsByUser(int userId, int pageNumber, int pageSize, String sortBy, String sortDirection);
	
}
