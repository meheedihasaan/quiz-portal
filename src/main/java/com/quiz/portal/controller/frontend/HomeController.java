package com.quiz.portal.controller.frontend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Quiz Portal");
		return "site-template/index";
	}
		
}
