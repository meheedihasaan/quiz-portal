package com.exam.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.exam.portal.entity.Question;
import com.exam.portal.entity.Quiz;
import com.exam.portal.exception.NotFoundException;
import com.exam.portal.repository.QuestionRepository;
import com.exam.portal.repository.QuizRepository;
import com.exam.portal.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private QuizRepository quizRepository;

	@Override
	public void createQuestion(Question question) {
		this.questionRepository.save(question);
	}

	@Override
	public Page<Question> getQuestions(int pageNumber, int pageSize, String sortBy, String sortDirection) {
		Sort sort = null;
		if(sortDirection.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		else if(sortDirection.equalsIgnoreCase("desc")) {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Question> questionPage = questionRepository.findAll(pageable);
		return questionPage;
	}
	
	@Override
	public Page<Question> getQuestionsByQuiz(int quizId, int pageNumber, int pageSize, String sortBy, String sortDirection){
		Sort sort = null;
		if(sortDirection.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		else if(sortDirection.equalsIgnoreCase("desc")) {
			sort = Sort.by(sortBy).descending();
		}
		
		Quiz quiz = this.quizRepository.findById(quizId).orElseThrow(()-> new NotFoundException("Quiz not found."));
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Question> questionPage = questionRepository.findByQuiz(pageable, quiz);
		return questionPage;
	}

	@Override
	public Question getQuestionById(int id) {
		Question question = this.questionRepository.findById(id).orElseThrow(()-> new NotFoundException("Question not found."));
		return question;
	}

	@Override
	public void updateQuestion(int id, Question question) {
		Question existingQuestion = this.questionRepository.findById(id).orElseThrow(()-> new NotFoundException("Question not found."));
		existingQuestion.setContent(question.getContent());
		existingQuestion.setOptionA(question.getOptionA());
		existingQuestion.setOptionB(question.getOptionB());
		existingQuestion.setOptionC(question.getOptionC());
		existingQuestion.setOptionD(question.getOptionD());
		existingQuestion.setAnswer(question.getAnswer());	
		this.questionRepository.save(existingQuestion);
	}

	@Override
	public void delteQuestion(int id) {
		Question question = this.questionRepository.findById(id).orElseThrow(()-> new NotFoundException("Question not found."));
		this.questionRepository.delete(question);
	}

}