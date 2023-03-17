package com.exam.portal.entity;

import java.util.HashSet;
import java.util.Set;

import com.exam.portal.constsant.EntityConstant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
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
@Table(name = EntityConstant.USER)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotEmpty(message = "Name is required.")
	@Size(min = 4, max = 50, message = "Name should be between 4 to 50 characters.")
	private String name;
	
	@NotEmpty(message = "Email is required.")
	@Email(message = "Please enter a valid email address.")
	private String email;
	
	@NotEmpty(message = "Phone number is required.")
	@Size(min = 10, max = 20, message = "Phone number should be between 10 to 20 characters.")
	private String phone;
	
	@NotEmpty(message = "Password is required.")
	@Size(min = 4, max = 32, message = "Password should be between 4 to 32 characters.")
	private String password;
	
	private String profileImage;
	
	private boolean isEnabled;
	
	private boolean isAgreed;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<UserRole> userRoles = new HashSet<>();
	
}
