package com.quiz.portal.service;

import com.quiz.portal.entity.Role;

public interface RoleService {

	void createRole(Role role);
	
	Role getRoleById(int id);
	
}
