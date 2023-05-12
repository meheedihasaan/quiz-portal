package com.quiz.portal.repository;

import com.quiz.portal.entity.Category;
import com.quiz.portal.entity.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, UUID> {

    Page<Quiz> findByCategory(Category category, Pageable pageable);

    Page<Quiz> findByIsActive(boolean isActive, Pageable pageable);

    List<Quiz> findByIsActive(boolean isActive);

    Page<Quiz> findByCategoryAndIsActive(Category category, boolean isActive, Pageable pageable);

    long countByIsActive(boolean isActive);

}
