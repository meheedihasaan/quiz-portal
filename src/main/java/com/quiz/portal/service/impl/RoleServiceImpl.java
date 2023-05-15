package com.quiz.portal.service.impl;

import com.quiz.portal.entity.Role;
import com.quiz.portal.exception.custom.NotFoundException;
import com.quiz.portal.repository.RoleRepository;
import com.quiz.portal.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public void createRole(Role role) {
        this.roleRepository.save(role);

    }

    @Override
    public Role getRoleById(UUID id) {
        return this.roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role not found!"));
    }

    @Override
    public Role getRoleByRoleName(String roleName) {
        return this.roleRepository.findByRoleName(roleName).orElseThrow(()-> new NotFoundException("Role not found"));
    }

    @Override
    public Boolean existsRoleByRoleName(String roleName) {
        return this.roleRepository.existsRoleByRoleName(roleName);
    }

}
