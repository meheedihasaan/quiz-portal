package com.exam.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.portal.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

}
