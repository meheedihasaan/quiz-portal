package com.exam.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.portal.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
