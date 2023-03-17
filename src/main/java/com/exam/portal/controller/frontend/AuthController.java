package com.exam.portal.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.exam.portal.entity.User;
import com.exam.portal.helper.Message;

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
		RedirectAttributes redirectAttributes,
		Model model
	) {
		try {
			if(bindingResult.hasErrors()) {
				System.out.println(bindingResult.toString());
				model.addAttribute("user", user);
				return "frontend/sign-up";
			}
			
			if(!isAgreed) {
				throw new Exception("You have not agreed with terms and condition. Please re-fill the form and check the terms and condition to sign up.");
			}
			return "redirect:/sign-up";
		}
		catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", new Message("alert-danger", "Something went wrong. "+e.getMessage()));
			return "redirect:/sign-up";
		}
	}
	
}
	
