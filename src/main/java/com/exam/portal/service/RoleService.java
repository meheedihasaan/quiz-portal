package com.exam.portal.service;

import com.exam.portal.entity.Role;

public interface RoleService {

	void createRole(Role role);
	
	Role getRoleById(int id);
	
}
