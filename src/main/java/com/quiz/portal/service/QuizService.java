package com.quiz.portal.service;

import com.quiz.portal.entity.Category;
import com.quiz.portal.entity.Quiz;
import com.quiz.portal.exception.custom.NotFoundException;
import com.quiz.portal.repository.CategoryRepository;
import com.quiz.portal.repository.QuizRepository;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuizService {

    private final QuizRepository quizRepository;

    private final CategoryRepository categoryRepository;

    public void createQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public Page<Quiz> getQuizzes(Pageable pageable) {
        return this.quizRepository.findAll(pageable);
    }

    public Page<Quiz> getQuizzesByCategory(UUID categoryId, Pageable pageable) {
        Category category = this.categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found."));
        return this.quizRepository.findByCategory(category, pageable);
    }

    public Page<Quiz> getPublishedQuizzes(Pageable pageable) {
        return this.quizRepository.findByIsActive(true, pageable);
    }

    public List<Quiz> getPublishedQuizzes() {
        return this.quizRepository.findByIsActive(true);
    }

    public Page<Quiz> getPublishedQuizzesByCategory(UUID categoryId, Pageable pageable) {
        Category category = this.categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found."));
        return this.quizRepository.findByCategoryAndIsActive(category, true, pageable);
    }

    public Quiz getQuizById(UUID id) {
        return this.quizRepository.findById(id).orElseThrow(() -> new NotFoundException("Quiz not found."));
    }

    public void updateQuiz(UUID id, Quiz quiz) {
        Quiz existingQuiz =
                this.quizRepository.findById(id).orElseThrow(() -> new NotFoundException("Quiz not found."));
        existingQuiz.setTitle(quiz.getTitle());
        existingQuiz.setCategory(quiz.getCategory());
        existingQuiz.setTotalQuestions(quiz.getTotalQuestions());
        existingQuiz.setTotalMarks(quiz.getTotalMarks());
        existingQuiz.setDuration(quiz.getDuration());
        existingQuiz.setActive(quiz.isActive());
        existingQuiz.setDescription(quiz.getDescription());
        this.quizRepository.save(existingQuiz);
    }

    public void deleteQuiz(UUID id) {
        Quiz quiz = this.quizRepository.findById(id).orElseThrow(() -> new NotFoundException("Quiz not found."));
        // quiz.getCategory().getQuizzes().remove(quiz);
        this.quizRepository.delete(quiz);
    }

    public long countQuizzes() {
        return this.quizRepository.count();
    }

    public long countPublishedQuizzes() {
        return this.quizRepository.countByIsActive(true);
    }
}
