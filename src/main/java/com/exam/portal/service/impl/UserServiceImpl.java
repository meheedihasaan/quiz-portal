package com.exam.portal.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.portal.entity.User;
import com.exam.portal.entity.UserRole;
import com.exam.portal.exception.NotFoundException;
import com.exam.portal.repository.RoleRepository;
import com.exam.portal.repository.UserRepository;
import com.exam.portal.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void createUser(User user, Set<UserRole> userRoles) {
		userRoles.forEach(userRole-> this.roleRepository.save(userRole.getRole())); //To save roles
		user.getUserRoles().addAll(userRoles);
		this.userRepository.save(user);
	}

	@Override
	public User getUserById(int id) {
		User user = this.userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found with id: "+id));
		return user;
	}
	
	@Override
	public User getUserByEmail(String email) {
		User user = this.userRepository.findByEmail(email);
		return user;
	}

	@Override
	public void deleteUser(int id) {
		User user = this.userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found with id: "+id));
		this.userRepository.delete(user);
	}
	
}
