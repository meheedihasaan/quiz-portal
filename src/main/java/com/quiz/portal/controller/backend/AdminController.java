package com.quiz.portal.controller.backend;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.quiz.portal.entity.Quiz;
import com.quiz.portal.entity.User;
import com.quiz.portal.service.CategoryService;
import com.quiz.portal.service.QuizService;
import com.quiz.portal.service.UserService;


@Controller
@RequestMapping("/backend")
public class AdminController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired 
	private QuizService quizService;
	
	public void loadCommonData(Model model, Principal principal) {
		String email = principal.getName();
		User user = this.userService.getUserByEmail(email);
		model.addAttribute("user", user);
	}

	@GetMapping
	public String dashboard(Model model, Principal principal) {
		loadCommonData(model, principal);
		model.addAttribute("title", "Dashboard");
		model.addAttribute("dashboardActive", "active");
		model.addAttribute("totalCategory", categoryService.countCategories());
		model.addAttribute("totalQuiz", quizService.countQuizzes());
		model.addAttribute("totalPublishedQuiz", quizService.countPublishedQuizzes());
		List<Quiz> quizzes = this.quizService.getPublishedQuizzes();
		model.addAttribute("quizzes", quizzes);
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
