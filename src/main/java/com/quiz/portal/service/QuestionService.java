package com.quiz.portal.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.quiz.portal.entity.Question;
import com.quiz.portal.entity.Quiz;

public interface QuestionService {

	void createQuestion(Question question);
	
	Page<Question> getQuestions(int pageNumber, int pageSize, String sortBy, String sortDirection);
	
	Page<Question> getQuestionsByQuiz(int quizId, int pageNumber, int pageSize, String sortBy, String sortDirection);
	
	List<Question> getQuestionsByQuiz(int quizId);
	
	Question getQuestionById(int id);
	
	void updateQuestion(int id, Question question);
	
	void delteQuestion(int id);
	
	long countByQuiz(Quiz quiz);
	
}
