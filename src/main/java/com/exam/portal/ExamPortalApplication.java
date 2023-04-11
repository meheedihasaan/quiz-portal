package com.exam.portal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.exam.portal.constsant.AppConstant;
import com.exam.portal.entity.Role;
import com.exam.portal.service.RoleService;


@SpringBootApplication
public class ExamPortalApplication implements CommandLineRunner {
	
	@Autowired
	private RoleService roleService;

	public static void main(String[] args) {
		SpringApplication.run(ExamPortalApplication.class, args);
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
