package com.exam.portal.entity;

import com.exam.portal.constsant.EntityConstant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
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
	
}
