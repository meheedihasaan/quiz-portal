package com.exam.portal.controller.frontend;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.exam.portal.constsant.AppConstant;
import com.exam.portal.entity.Role;
import com.exam.portal.entity.User;
import com.exam.portal.entity.UserRole;
import com.exam.portal.exception.AlreadyExistsException;
import com.exam.portal.helper.Message;
import com.exam.portal.service.UserService;

import jakarta.validation.Valid;

@Controller
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/sign-up")
	public String viewSignUpPage(Model model) {
		model.addAttribute("title", "Sign up");
		model.addAttribute("user", new User());
		return "site-template/sign-up";
	}
	
	@PostMapping("/sign-up")
	public String signUpProcess(
		@Valid @ModelAttribute User user, 
		BindingResult bindingResult,
		@RequestParam(value = "isAgreed", defaultValue = "false") boolean isAgreed,
		RedirectAttributes redirectAttributes,
		Model model
	) {
		try {
			if(bindingResult.hasErrors()) {
				System.out.println(bindingResult.toString());
				model.addAttribute("user", user);
				return "site-template/sign-up";
			}
			
			if(!isAgreed) {
				throw new Exception("You have not agreed with terms and condition. Please re-fill the form and check the terms and condition to sign up.");
			}
			
			User existingUser = this.userService.getUserByEmail(user.getEmail());
			if(existingUser != null) {
				throw new AlreadyExistsException("User already exists with email "+user.getEmail()+".");
			}
			else {
				Role role = new Role();
				role.setId(AppConstant.NORMAL_ID);
				role.setName(AppConstant.NORMAL);
				
				UserRole userRole = new UserRole();
				userRole.setUser(user);
				userRole.setRole(role);
				
				Set<UserRole> userRoles = new HashSet<>();
				userRoles.add(userRole);
				
				user.setPassword(passwordEncoder.encode(user.getPassword()));
				user.setEnabled(true);
				user.setAgreed(true);
				user.setProfileImage("userProfile.jpg");
				this.userService.createUser(user, userRoles);
				redirectAttributes.addFlashAttribute("message", new Message("alert-primary", "Congratulations! You are successfully registered."));
				return "redirect:/sign-up";
			}
		}
		catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", new Message("alert-danger", "Something went wrong! "+e.getMessage()));
			return "redirect:/sign-up";
		}
	}
	
	@GetMapping("/sign-in")
	public String viewSignInPage(Model model) {
		model.addAttribute("title", "Sign in");
		return "site-template/sign-in";
	}
	
}
	
