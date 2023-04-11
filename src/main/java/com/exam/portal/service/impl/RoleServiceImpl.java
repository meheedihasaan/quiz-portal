package com.exam.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.portal.entity.Role;
import com.exam.portal.exception.NotFoundException;
import com.exam.portal.repository.RoleRepository;
import com.exam.portal.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void createRole(Role role) {
		this.roleRepository.save(role);
		
	}

	@Override
	public Role getRoleById(int id) {
		Role role = this.roleRepository.findById(id).orElseThrow(()-> new NotFoundException("Role not found!"));
		return role;
	}	
	
}
