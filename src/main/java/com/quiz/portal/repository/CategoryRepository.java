package com.quiz.portal.repository;

import com.quiz.portal.entity.Category;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Category findByNameIgnoreCase(String name);
}
