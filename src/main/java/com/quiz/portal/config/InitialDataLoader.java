package com.quiz.portal.config;

import com.quiz.portal.constsant.AppConstant;
import com.quiz.portal.entity.Role;
import com.quiz.portal.entity.User;
import com.quiz.portal.service.RoleService;
import com.quiz.portal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class InitialDataLoader implements ApplicationListener<ApplicationContextEvent> {

    private final UserService userService;

    private final RoleService roleService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        if (!isRoleAlreadyExists(AppConstant.ADMIN)) {
            Role adminRole = new Role();
            adminRole.setRoleName(AppConstant.ADMIN);
            roleService.createRole(adminRole);
        }

        if (!isRoleAlreadyExists(AppConstant.NORMAL)) {
            Role normalRole = new Role();
            normalRole.setRoleName(AppConstant.NORMAL);
            roleService.createRole(normalRole);
        }

        if (!isSuperAdminAlreadyExists(AppConstant.SUPER_ADMIN_EMAIL)) {
            Set<Role> roles = new HashSet<>();
            roles.add(roleService.getRoleByRoleName(AppConstant.ADMIN));

            User superAdmin = new User();
            superAdmin.setName(AppConstant.SUPER_ADMIN_NAME);
            superAdmin.setEmail(AppConstant.SUPER_ADMIN_EMAIL);
            superAdmin.setPassword(passwordEncoder.encode(AppConstant.SUPER_ADMIN_PASSWORD));
            superAdmin.setPhone(AppConstant.SUPER_ADMIN_PHONE);
            superAdmin.setRoles(roles);
            superAdmin.setEnabled(true);
            superAdmin.setAgreed(true);
            userService.createUser(superAdmin);
        }
    }

    private Boolean isRoleAlreadyExists(String roleName) {
        return roleService.existsRoleByRoleName(roleName);
    }

    private Boolean isSuperAdminAlreadyExists(String email) {
        return userService.existsUserByEmail(email);
    }

}
