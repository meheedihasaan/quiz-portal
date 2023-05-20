package com.quiz.portal.entity;

import com.quiz.portal.constsant.EntityConstant;
import com.quiz.portal.model.audit.AuditModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = EntityConstant.QUESTION)
public class Question extends AuditModel<String> {

    @NotEmpty(message = "Content is required.")
    @Size(min = 10, max = 255, message = "Content should be between 10 to 255 characters.")
    private String content;

    @NotEmpty(message = "Option is required.")
    @Size(max = 255, message = "Option should be less than 255 characters.")
    private String optionA;

    @NotEmpty(message = "Option is required.")
    @Size(max = 255, message = "Option should be less than 255 characters.")
    private String optionB;

    @NotEmpty(message = "Option is required.")
    @Size(max = 255, message = "Option should be less than 255 characters.")
    private String optionC;

    @NotEmpty(message = "Option is required.")
    @Size(max = 255, message = "Option should be less than 255 characters.")
    private String optionD;

    @NotEmpty(message = "Answer is required.")
    @Size(max = 255, message = "Answer should be less than 255 characters.")
    private String answer;

    @Transient
    private String userAnswer;

    @ManyToOne(fetch = FetchType.EAGER)
    private Quiz quiz;
}
