package com.quiz.portal.controller.frontend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quiz.portal.entity.User;
import com.quiz.portal.exception.custom.AlreadyExistsException;
import com.quiz.portal.helper.Message;
import com.quiz.portal.service.UserService;

import jakarta.validation.Valid;

@RequiredArgsConstructor
@Controller
public class AuthController {

	private final UserService userService;

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
				model.addAttribute("title", "Sign up");
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
				this.userService.signUpUser(user);
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
	
