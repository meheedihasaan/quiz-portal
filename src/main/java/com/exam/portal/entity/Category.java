package com.exam.portal.entity;

import com.exam.portal.constsant.EntityConstant;

import jakarta.persistence.Column;
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
@Table(name = EntityConstant.CATEGORY)
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotEmpty(message = "Category name is required.")
	@Size(min = 4, max = 50, message = "Category name should be between 4 to 50 characters.")
	private String name;
	
	@Column(length = 5000)
	@NotEmpty(message = "Category Description is required.")
	@Size(min = 10, max = 5000, message = "Category description should be between 10 to 5000 characters.")
	private String description;
	
}