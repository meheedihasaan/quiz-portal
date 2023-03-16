package com.exam.portal;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.exam.portal.entity.Role;
import com.exam.portal.entity.User;
import com.exam.portal.entity.UserRole;
import com.exam.portal.service.UserService;


@SpringBootApplication
public class ExamPortalApplication implements CommandLineRunner {
	
	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(ExamPortalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setName("Zahid Hasan");
		user.setEmail("zahid@gmail.com");
		user.setPhone("01309411635");
		user.setDescription("I am a student");
		user.setProfileImage("userProfile.jpg");
		user.setEnable(true);
		
		Role role = new Role();
		role.setId(101);
		role.setName("ADMIN");
		
		UserRole userRole = new UserRole();
		userRole.setUser(user);
		userRole.setRole(role);
		
		Set<UserRole> userRoles = new HashSet<>();
		userRoles.add(userRole);
		
		this.userService.createUser(user, userRoles);
		System.out.println("User is created");
	}

}
