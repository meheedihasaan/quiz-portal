package com.quiz.portal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.quiz.portal.entity.Role;
import com.quiz.portal.exception.custom.NotFoundException;
import com.quiz.portal.repository.RoleRepository;
import com.quiz.portal.service.RoleService;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;

	public void createRole(Role role) {
		this.roleRepository.save(role);
		
	}

	@Override
	public Role getRoleById(int id) {
		Role role = this.roleRepository.findById(id).orElseThrow(()-> new NotFoundException("Role not found!"));
		return role;
	}	
	
}
