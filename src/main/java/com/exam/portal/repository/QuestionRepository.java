package com.exam.portal.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.portal.entity.Question;
import com.exam.portal.entity.Quiz;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

	Page<Question> findAll(Pageable pageable);
	
	Page<Question> findByQuiz(Pageable pageable, Quiz quiz);
	
	List<Question> findByQuiz(Quiz quiz);
	
	long countByQuiz(Quiz quiz);
	
}
