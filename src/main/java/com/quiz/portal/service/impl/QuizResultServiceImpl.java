package com.quiz.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.quiz.portal.entity.QuizResult;
import com.quiz.portal.entity.User;
import com.quiz.portal.exception.NotFoundException;
import com.quiz.portal.repository.QuizResultRepository;
import com.quiz.portal.repository.UserRepository;
import com.quiz.portal.service.QuizResultService;

@Service
public class QuizResultServiceImpl implements QuizResultService {
	
	@Autowired
	private QuizResultRepository quizResultRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public QuizResult createQuizResult(QuizResult quizResult) {
		QuizResult savedQuizResult = this.quizResultRepository.save(quizResult);
		return savedQuizResult;
	}
	
	@Override
	public Page<QuizResult> getQuizResults(int pageNumber, int pageSize, String sortBy, String sortDirection) {
		Sort sort = null;
		if(sortDirection.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		else if(sortDirection.equalsIgnoreCase("desc")) {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<QuizResult> quizResultPage = this.quizResultRepository.findAll(pageable);
		return quizResultPage;
	}
	
	@Override
	public QuizResult getQuizResultById(int id) {
		QuizResult quizResult = this.quizResultRepository.findById(id).orElseThrow(()-> new NotFoundException("Quiz result not found!"));
		return quizResult;
	}

	@Override
	public Page<QuizResult> getQuizResultsByUser(int userId, int pageNumber, int pageSize, String sortBy, String sortDirection) {
		User user = this.userRepository.findById(userId).orElseThrow(()-> new NotFoundException("User not found!"));
		
		Sort sort = null;
		if(sortDirection.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		}
		else if(sortDirection.equalsIgnoreCase("desc")) {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<QuizResult> quizResultPage = this.quizResultRepository.findByUser(user, pageable);
		return quizResultPage;
	}

}