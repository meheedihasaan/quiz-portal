package com.quiz.portal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quiz.portal.constsant.EntityConstant;
import com.quiz.portal.model.audit.AuditModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = EntityConstant.QUIZ)
public class Quiz extends AuditModel<String> {

    @NotEmpty(message = "Quiz title is required.")
    @Size(min = 4, max = 50, message = "Quiz title should be between 4 to 50 characters.")
    private String title;

    @Range(min = 5, message = "Total marks should be greater than or equal 5.")
    private int totalMarks;

    @Range(min = 5, max = 100, message = "Total questions should be between 5 to 100.")
    private int totalQuestions;

    @Range(min = 5, max = 100, message = "Quiz duration should be between 1 to 100 minutes.")
    private int duration;

    @NotEmpty(message = "Quiz description is required.")
    @Size(min = 10, max = 5000, message = "Quiz description should be between 10 to 5000 characters.")
    private String description;

    private boolean isActive;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Question> questions = new HashSet<>();

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<QuizResult> quizResults = new HashSet<>();
}
