package com.quiz.portal.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quiz.portal.entity.Question;
import com.quiz.portal.entity.Quiz;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
	
	Page<Question> findByQuiz(Quiz quiz, Pageable pageable);
	
	List<Question> findByQuiz(Quiz quiz);
	
	long countByQuiz(Quiz quiz);
	
}
