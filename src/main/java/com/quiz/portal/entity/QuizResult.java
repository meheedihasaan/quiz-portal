package com.quiz.portal.entity;

import com.quiz.portal.constsant.EntityConstant;
import jakarta.persistence.*;
import java.util.Date;
import java.util.UUID;
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
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

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
