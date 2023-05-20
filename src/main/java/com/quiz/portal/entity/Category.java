package com.quiz.portal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quiz.portal.constsant.EntityConstant;
import com.quiz.portal.model.audit.AuditModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = EntityConstant.CATEGORY)
public class Category extends AuditModel<String> {

    @NotEmpty(message = "Category name is required.")
    @Size(min = 4, max = 50, message = "Category name should be between 4 to 50 characters.")
    private String name;

    @Column(length = 5000)
    @NotEmpty(message = "Category Description is required.")
    @Size(min = 10, max = 5000, message = "Category description should be between 10 to 5000 characters.")
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    Set<Quiz> quizzes = new LinkedHashSet<>();
}
