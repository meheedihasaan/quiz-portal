package com.exam.portal.entity;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.Range;

import com.exam.portal.constsant.EntityConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = EntityConstant.QUIZ)
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotEmpty(message = "Quiz title is required.")
	@Size(min = 4, max = 50, message = "Quiz title should be between 4 to 50 characters.")
	private String title;
	
	@Range(min = 5, message = "Total marks should be greater than or equal 5.")
	private int totalMarks;
	
	@Range(min = 5, max = 100, message = "Toal questions should be between 5 to 100.")
	private int totalQuestions;
	
	@Range(min = 1, max = 100, message = "Quiz duration should be between 1 to 100 minutes.")
	private int duration;
	
	@NotEmpty(message = "Quiz description is required.")
	@Size(min = 10, max = 5000, message = "Quiz description should be beteween 10 to 5000 charcters.")
	private String description;
	
	private boolean isActive;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Category category;
	
	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JsonIgnore
	private Set<Question> qustions = new HashSet<>();
	
}
