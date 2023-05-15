package com.quiz.portal.service;

import com.quiz.portal.entity.QuizResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface QuizResultService {

    QuizResult createQuizResult(QuizResult quizResult);

    Page<QuizResult> getQuizResults(Pageable pageable);

    QuizResult getQuizResultById(UUID id);

    Page<QuizResult> getQuizResultsByUser(UUID userId, Pageable pageable);

    long countAttemptsByUser(UUID userId);

    long countParticipants();

    double getAvgAccuracyByUser(UUID userId);

}