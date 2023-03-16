package com.exam.portal.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.portal.entity.User;
import com.exam.portal.entity.UserRole;
import com.exam.portal.exception.AlreadyExistsException;
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
	public void createUser(User user, Set<UserRole> userRoles) throws Exception {
		User existingUser = this.userRepository.findByEmail(user.getEmail());
		if(existingUser != null) {
			System.out.println("User already exists with email: "+user.getEmail());
			throw new AlreadyExistsException("User already exists with email: "+user.getEmail());
		}
		
		userRoles.forEach(userRole-> this.roleRepository.save(userRole.getRole())); //To save roles
		user.getUserRoles().addAll(userRoles); 
		this.userRepository.save(user);
	}
	
}
