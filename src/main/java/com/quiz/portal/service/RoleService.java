package com.quiz.portal.service;

import com.quiz.portal.entity.Role;
import java.util.UUID;

public interface RoleService {

    void createRole(Role role);

    Role getRoleById(UUID id);

    Role getRoleByRoleName(String roleName);

    Boolean existsRoleByRoleName(String roleName);
}
