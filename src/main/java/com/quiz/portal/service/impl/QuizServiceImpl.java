package com.quiz.portal.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quiz.portal.entity.Category;
import com.quiz.portal.entity.Quiz;
import com.quiz.portal.exception.NotFoundException;
import com.quiz.portal.repository.CategoryRepository;
import com.quiz.portal.repository.QuizRepository;
import com.quiz.portal.service.QuizService;

@RequiredArgsConstructor
@Service
public class QuizServiceImpl implements QuizService {

	private final QuizRepository quizRepository;

	private final CategoryRepository categoryRepository;

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
	public Page<Quiz> getQuizzesByCategory(int categoryId, int pageNumber, int pageSize, String sortBy, String sortDirection) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new NotFoundException("Category not found."));
		
		Sort sort = null;
		if(sortDirection.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		else if(sortDirection.equalsIgnoreCase("desc")) {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Quiz> quizPage = this.quizRepository.findByCategory(category, pageable);
		return quizPage;
	}
	
	@Override
	public Page<Quiz> getPublishedQuizzes(int pageNumber, int pageSize, String sortBy, String sortDirection) {
		Sort sort = null;
		if(sortDirection.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		else if(sortDirection.equalsIgnoreCase("desc")) {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Quiz> quizPage = this.quizRepository.findByIsActive(true, pageable);
		return quizPage;
	}
	
	@Override
	public List<Quiz> getPublishedQuizzes() {
		List<Quiz> quizzes = this.quizRepository.findByIsActive(true);
		return quizzes;
	}
	
	@Override
	public Page<Quiz> getPublishedQuizzesByCategory(int categoryId, int pageNumber, int pageSize, String sortBy, String sortDirection) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new NotFoundException("Category not found."));
		
		Sort sort = null;
		if(sortDirection.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		else if(sortDirection.equalsIgnoreCase("desc")) {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Quiz> quizPage = this.quizRepository.findByCategoryAndIsActive(category, true, pageable);
		return quizPage;
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
	
	@Override
	public long countQuizzes() {
		long totalQuiz = this.quizRepository.count();
		return totalQuiz;
	}
	
	@Override
	public long countPublishedQuizzes() {
		long totalPublishedQuiz = this.quizRepository.countByIsActive(true);
		return totalPublishedQuiz;
	}

}
