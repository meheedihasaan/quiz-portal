package com.exam.portal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.portal.entity.QuizResult;
import com.exam.portal.entity.User;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Integer> {

	Page<QuizResult> findByUser(User user, Pageable pageable);
	
}
