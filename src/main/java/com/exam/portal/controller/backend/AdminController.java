package com.exam.portal.controller.backend;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.exam.portal.entity.Role;
import com.exam.portal.entity.User;
import com.exam.portal.service.UserService;


@Controller
@RequestMapping("/backend")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	public void loadCommonData(Model model, Principal principal) {
		String email = principal.getName();
		User user = this.userService.getUserByEmail(email);
		model.addAttribute("user", user);
		List<Role> roles = user.getUserRoles().stream().map((userRole)-> userRole.getRole()).collect(Collectors.toList());
		model.addAttribute("role", roles.get(0).getName());
	}

	@GetMapping
	public String dashboard(Model model, Principal principal) {
		loadCommonData(model, principal);
		model.addAttribute("title", "Dashboard");
		model.addAttribute("dashboardActive", "active");
		return "admin-template/index";
	}
	
	@GetMapping("/my-profile/{name}")
	public String viewMyProfile(Model model, Principal principal) {
		loadCommonData(model, principal);
		model.addAttribute("title", "My Profile");
		model.addAttribute("myProfileActive", "active");
		return "admin-template/my-profile";
	}
	
}
