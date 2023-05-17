package com.quiz.portal.service;

import com.quiz.portal.entity.Quiz;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuizService {

    void createQuiz(Quiz quiz);

    Page<Quiz> getQuizzes(Pageable pageable);

    Page<Quiz> getQuizzesByCategory(UUID categoryId, Pageable pageable);

    Page<Quiz> getPublishedQuizzes(Pageable pageable);

    List<Quiz> getPublishedQuizzes();

    Page<Quiz> getPublishedQuizzesByCategory(UUID categoryId, Pageable pageable);

    Quiz getQuizById(UUID id);

    void updateQuiz(UUID id, Quiz quiz);

    void deleteQuiz(UUID id);

    long countQuizzes();

    long countPublishedQuizzes();
}
