package com.quiz.portal.service;

import com.quiz.portal.entity.Question;
import com.quiz.portal.entity.Quiz;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {

    void createQuestion(Question question);

    Page<Question> getQuestions(Pageable pageable);

    Page<Question> getQuestionsByQuiz(UUID quizId, Pageable pageable);

    List<Question> getQuestionsByQuiz(UUID quizId);

    Question getQuestionById(UUID id);

    void updateQuestion(UUID id, Question question);

    void deleteQuestion(UUID id);

    long countByQuiz(Quiz quiz);
}
