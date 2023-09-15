package com.quiz.portal.service;

import com.quiz.portal.entity.Role;
import com.quiz.portal.exception.custom.NotFoundException;
import com.quiz.portal.repository.RoleRepository;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public void createRole(Role role) {
        this.roleRepository.save(role);
    }

    public Role getRoleById(UUID id) {
        return this.roleRepository.findById(id).orElseThrow(() -> new NotFoundException("Role not found!"));
    }

    public Role getRoleByRoleName(String roleName) {
        return this.roleRepository.findByRoleName(roleName).orElseThrow(() -> new NotFoundException("Role not found"));
    }

    public Boolean existsRoleByRoleName(String roleName) {
        return this.roleRepository.existsRoleByRoleName(roleName);
    }
}
