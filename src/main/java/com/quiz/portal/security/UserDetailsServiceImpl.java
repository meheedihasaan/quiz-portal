package com.quiz.portal.security;

import com.quiz.portal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.quiz.portal.entity.User;
import com.quiz.portal.exception.custom.NotFoundException;
import com.quiz.portal.service.UserService;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findByEmail(username).orElseThrow(()-> new NotFoundException("User not found with email "+username));
		return new UserDetailsImpl(user);
	}

}
