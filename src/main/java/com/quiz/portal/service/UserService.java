package com.quiz.portal.service;

import com.quiz.portal.entity.User;
import java.util.UUID;

public interface UserService {

    void saveUser(User user);

    void createUser(User user);

    User getUserById(UUID id);

    User getUserByEmail(String email);

    User getUserByEmailWithException(String email);

    Boolean existsUserByEmail(String email);

    void deleteUser(UUID id);

    void signUpUser(User user);
}
