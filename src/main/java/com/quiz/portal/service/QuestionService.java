package com.quiz.portal.service;

import com.quiz.portal.entity.Question;
import com.quiz.portal.entity.Quiz;
import com.quiz.portal.exception.custom.NotFoundException;
import com.quiz.portal.repository.QuestionRepository;
import com.quiz.portal.repository.QuizRepository;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    private final QuizRepository quizRepository;

    public void createQuestion(Question question) {
        this.questionRepository.save(question);
    }

    public Page<Question> getQuestions(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    public Page<Question> getQuestionsByQuiz(UUID quizId, Pageable pageable) {
        Quiz quiz = this.quizRepository.findById(quizId).orElseThrow(() -> new NotFoundException("Quiz not found."));
        return questionRepository.findByQuiz(quiz, pageable);
    }

    public List<Question> getQuestionsByQuiz(UUID quizId) {
        Quiz quiz = this.quizRepository.findById(quizId).orElseThrow(() -> new NotFoundException("Quiz not found."));
        return this.questionRepository.findByQuiz(quiz);
    }

    public Question getQuestionById(UUID id) {
        return this.questionRepository.findById(id).orElseThrow(() -> new NotFoundException("Question not found."));
    }

    public void updateQuestion(UUID id, Question question) {
        Question existingQuestion =
                this.questionRepository.findById(id).orElseThrow(() -> new NotFoundException("Question not found."));
        existingQuestion.setContent(question.getContent());
        existingQuestion.setOptionA(question.getOptionA());
        existingQuestion.setOptionB(question.getOptionB());
        existingQuestion.setOptionC(question.getOptionC());
        existingQuestion.setOptionD(question.getOptionD());
        existingQuestion.setAnswer(question.getAnswer());
        this.questionRepository.save(existingQuestion);
    }

    public void deleteQuestion(UUID id) {
        Question question =
                this.questionRepository.findById(id).orElseThrow(() -> new NotFoundException("Question not found."));
        this.questionRepository.delete(question);
    }

    public long countByQuiz(Quiz quiz) {
        return this.questionRepository.countByQuiz(quiz);
    }
}
