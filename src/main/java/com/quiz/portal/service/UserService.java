package com.quiz.portal.service;

import com.quiz.portal.constsant.AppConstant;
import com.quiz.portal.entity.Role;
import com.quiz.portal.entity.User;
import com.quiz.portal.exception.custom.NotFoundException;
import com.quiz.portal.repository.RoleRepository;
import com.quiz.portal.repository.UserRepository;

import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    public void createUser(User user) {
        this.userRepository.save(user);
    }

    public User getUserById(UUID id) {
        return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found."));
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElse(null);
    }

    public User getUserByEmailWithException(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found."));
    }

    public Boolean existsUserByEmail(String email) {
        return this.userRepository.existsUserByEmail(email);
    }

    public void deleteUser(UUID id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found."));
        this.userRepository.delete(user);
    }

    public void signUpUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setAgreed(true);
        user.setProfileImage("userProfile.jpg");
        Role role = this.roleRepository
                .findByRoleName(AppConstant.NORMAL)
                .orElseThrow(() -> new NotFoundException("Role not found!"));
        user.setRoles(Set.of(role));
        this.userRepository.save(user);
    }
}
