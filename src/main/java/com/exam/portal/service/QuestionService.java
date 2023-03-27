package com.exam.portal.service;

import org.springframework.data.domain.Page;

import com.exam.portal.entity.Question;

public interface QuestionService {

	void createQuestion(Question question);
	
	Page<Question> getQuestions(int pageNumber, int pageSize, String sortBy, String sortDirection);
	
	Page<Question> getQuestionsByQuiz(int quizId, int pageNumber, int pageSize, String sortBy, String sortDirection);
	
	Question getQuestionById(int id);
	
	void updateQuestion(int id, Question question);
	
	void delteQuestion(int id);
	
}
