package com.exam.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.portal.entity.QuizResult;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, Integer> {

}
