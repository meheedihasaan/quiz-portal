package com.exam.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exam.portal.constsant.AppConstant;
import com.exam.portal.entity.Role;
import com.exam.portal.entity.User;
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
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void createUser(User user) {		
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
	
	@Override
	public void signUpUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		user.setAgreed(true);
		user.setProfileImage("userProfile.jpg");
		Role role = this.roleRepository.findById(AppConstant.NORMAL_ID).get();
		user.getRoles().add(role);
		this.userRepository.save(user);
	}
	
}
