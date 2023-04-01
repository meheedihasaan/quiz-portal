package com.exam.portal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.exam.portal.entity.Quiz;
import com.exam.portal.exception.NotFoundException;
import com.exam.portal.repository.QuizRepository;
import com.exam.portal.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService {
	
	@Autowired
	private QuizRepository quizRepository;

	@Override
	public void createQuiz(Quiz quiz) {
		quizRepository.save(quiz);
	}

	@Override
	public Page<Quiz> getQuizzes(int pageNumber, int pageSize, String sortBy, String sortDirection) {
		Sort sort = null;
		if(sortDirection.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		else if(sortDirection.equalsIgnoreCase("desc")) {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Quiz> quizPage = this.quizRepository.findAll(pageable);
		return quizPage;
	}
	
	@Override
	public List<Quiz> getPublishedQuizzes() {
		List<Quiz> quizzes = this.quizRepository.findByIsActive(true);
		return quizzes;
	}

	@Override
	public Quiz getQuizById(int id) {
		Quiz quiz = this.quizRepository.findById(id).orElseThrow(()-> new NotFoundException("Quiz not found."));
		return quiz;
	}

	@Override
	public void updateQuiz(int id, Quiz quiz) {
		Quiz existingQuiz = this.quizRepository.findById(id).orElseThrow(() -> new NotFoundException("Quiz not found."));
		existingQuiz.setTitle(quiz.getTitle());
		existingQuiz.setCategory(quiz.getCategory());
		existingQuiz.setTotalQuestions(quiz.getTotalQuestions());
		existingQuiz.setTotalMarks(quiz.getTotalMarks());
		existingQuiz.setDuration(quiz.getDuration());
		existingQuiz.setActive(quiz.isActive());
		existingQuiz.setDescription(quiz.getDescription());
		this.quizRepository.save(existingQuiz);
	}

	@Override
	public void deleteQuiz(int id) {
		Quiz quiz = this.quizRepository.findById(id).orElseThrow(() -> new NotFoundException("Quiz not found."));
		//quiz.getCategory().getQuizzes().remove(quiz);
		this.quizRepository.delete(quiz);
	}

}
