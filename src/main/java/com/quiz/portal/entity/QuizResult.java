package com.quiz.portal.entity;

import com.quiz.portal.constsant.EntityConstant;
import com.quiz.portal.model.audit.AuditModel;
import jakarta.persistence.*;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = EntityConstant.QUIZ_RESULT)
public class QuizResult extends AuditModel<String> {

    private int obtainedMarks;

    private int attemptedQuestions;

    private int correctAnswers;

    private int accuracy;

    private Date date = new Date();

    @ManyToOne(fetch = FetchType.EAGER)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
}
