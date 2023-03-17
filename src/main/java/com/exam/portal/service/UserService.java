package com.exam.portal.service;

import java.util.Set;

import com.exam.portal.entity.User;
import com.exam.portal.entity.UserRole;

public interface UserService {

	void createUser(User user, Set<UserRole> userRoles) throws Exception;
	
	User getUserById(int id);
	
	User getUserByEmail(String email);
	
	void deleteUser(int id);
	
}
