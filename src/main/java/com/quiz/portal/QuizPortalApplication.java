package com.quiz.portal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.quiz.portal.constsant.AppConstant;
import com.quiz.portal.entity.Role;
import com.quiz.portal.service.RoleService;


@SpringBootApplication
public class QuizPortalApplication implements CommandLineRunner {
	
	@Autowired
	private RoleService roleService;

	public static void main(String[] args) {
		SpringApplication.run(QuizPortalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Role admin = new Role();
		admin.setId(AppConstant.ADMIN_ID);
		admin.setName(AppConstant.ADMIN);
		roleService.createRole(admin);
		
		Role normal = new Role();
		normal.setId(AppConstant.NORMAL_ID);
		normal.setName(AppConstant.NORMAL);
		roleService.createRole(normal);
	}

}
