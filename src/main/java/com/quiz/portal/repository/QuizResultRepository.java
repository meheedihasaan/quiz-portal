package com.quiz.portal.repository;

import com.quiz.portal.entity.QuizResult;
import com.quiz.portal.entity.User;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizResultRepository extends JpaRepository<QuizResult, UUID> {

    Page<QuizResult> findByUser(User user, Pageable pageable);

    long countByUser(User user);

    @Query("SELECT AVG(c.accuracy) FROM QuizResult c WHERE c.user = :user")
    double getAvgAccuracyByUser(@Param("user") User user);
}
