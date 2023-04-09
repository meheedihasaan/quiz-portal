package com.exam.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.portal.entity.QuizResult;
import com.exam.portal.repository.QuizResultRepository;
import com.exam.portal.service.QuizResultService;

@Service
public class QuizResultServiceImpl implements QuizResultService {
	
	@Autowired
	private QuizResultRepository quizResultRepository;

	@Override
	public void createQuizResult(QuizResult quizResult) {
		this.quizResultRepository.save(quizResult);
	}

}
