package com.exam.portal.entity;

import java.util.Date;

import com.exam.portal.constsant.EntityConstant;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
	private int id;
	
	private int obtainedMarks;
	
	private int attemptedQuestions;
	
	private int correctAnswers;
	
	private double accuracy;
	
	private Date date = new Date();
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Quiz quiz;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
}
