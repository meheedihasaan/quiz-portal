package com.exam.portal.entity;

import java.util.HashSet;
import java.util.Set;

import com.exam.portal.constsant.EntityConstant;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Table(name = EntityConstant.ROLE)
public class Role {

	@Id
	private int id;
	
	private String name;
	
	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
	private Set<User> users = new HashSet<>();
	
}
