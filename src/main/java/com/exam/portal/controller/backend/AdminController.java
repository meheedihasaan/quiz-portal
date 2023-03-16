package com.exam.portal.controller.backend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/backend")
public class AdminController {

	@GetMapping
	public String dashboard(Model model) {
		model.addAttribute("title", "Dashboard");
		return "backend/index";
	}
	
}
