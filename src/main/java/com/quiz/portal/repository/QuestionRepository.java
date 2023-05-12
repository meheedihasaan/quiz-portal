package com.quiz.portal.repository;

import com.quiz.portal.entity.Question;
import com.quiz.portal.entity.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {

    Page<Question> findByQuiz(Quiz quiz, Pageable pageable);

    List<Question> findByQuiz(Quiz quiz);

    long countByQuiz(Quiz quiz);

}
