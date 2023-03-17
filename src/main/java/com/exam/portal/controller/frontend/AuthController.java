package com.exam.portal.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.exam.portal.entity.User;

import jakarta.validation.Valid;

@Controller
public class AuthController {

	@GetMapping("/sign-up")
	public String viewSignUpPage(Model model) {
		model.addAttribute("title", "Sign up");
		model.addAttribute("user", new User());
		return "frontend/sign-up";
	}
	
	@PostMapping("/sign-up")
	public String signUpProcess(
		@Valid @ModelAttribute User user, BindingResult bindingResult,
		@RequestParam(value = "isAgreed", defaultValue = "false") boolean isAgreed,
		Model model
	) {
		System.out.println(user.getName()+" "+user.getEmail());
		if(bindingResult.hasErrors()) {
			System.out.println(bindingResult.toString());
			model.addAttribute("user", user);
			return "frontend/sign-up";
		}
		return "redirect:/sign-up";
	}
	
}
