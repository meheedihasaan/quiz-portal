package com.quiz.portal.service;

import com.quiz.portal.entity.User;

public interface UserService {

	void createUser(User user);
	
	User getUserById(int id);
	
	User getUserByEmail(String email);
	
	void deleteUser(int id);
	
	void signUpUser(User user);
	
}