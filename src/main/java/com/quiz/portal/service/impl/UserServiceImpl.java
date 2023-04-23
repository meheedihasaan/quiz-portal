package com.quiz.portal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quiz.portal.constsant.AppConstant;
import com.quiz.portal.entity.Role;
import com.quiz.portal.entity.User;
import com.quiz.portal.exception.NotFoundException;
import com.quiz.portal.repository.RoleRepository;
import com.quiz.portal.repository.UserRepository;
import com.quiz.portal.service.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;

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
