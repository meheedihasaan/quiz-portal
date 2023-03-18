package com.exam.portal.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.exam.portal.entity.User;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Exam Portal");
		return "site-template/index";
	}
		
}
