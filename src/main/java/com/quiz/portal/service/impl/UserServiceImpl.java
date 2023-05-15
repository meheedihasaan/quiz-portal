package com.quiz.portal.service.impl;

import com.quiz.portal.constsant.AppConstant;
import com.quiz.portal.entity.Role;
import com.quiz.portal.entity.User;
import com.quiz.portal.exception.custom.NotFoundException;
import com.quiz.portal.repository.RoleRepository;
import com.quiz.portal.repository.UserRepository;
import com.quiz.portal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public void createUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public User getUserById(UUID id) {
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found."));
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found."));
    }

    @Override
    public Boolean existsUserByEmail(String email) {
        return this.userRepository.existsUserByEmail(email);
    }

    @Override
    public void deleteUser(UUID id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found."));
        this.userRepository.delete(user);
    }

    @Override
    public void signUpUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setAgreed(true);
        user.setProfileImage("userProfile.jpg");
        Role role = this.roleRepository.findByRoleName(AppConstant.NORMAL).orElseThrow(()-> new NotFoundException("Role not found!"));
        user.getRoles().add(role);
        this.userRepository.save(user);
    }

}
