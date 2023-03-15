package com.exam.portal.controller.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/backend")
public class AdminController {

	@GetMapping
	public String dashboard() {
		return "backend/sample-page";
	}
	
}
