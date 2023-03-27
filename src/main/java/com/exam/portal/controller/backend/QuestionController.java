package com.exam.portal.controller.backend;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.exam.portal.entity.Question;
import com.exam.portal.entity.Quiz;
import com.exam.portal.entity.Role;
import com.exam.portal.entity.User;
import com.exam.portal.service.QuestionService;
import com.exam.portal.service.QuizService;
import com.exam.portal.service.UserService;

@Controller
@RequestMapping("/backend")
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private QuizService quizService;
	
	@Autowired
	private UserService userService;
	
	public void loadCommonData(Model model, Principal principal) {
		String username = principal.getName();
		User user = userService.getUserByEmail(username);
		model.addAttribute("user", user);
		List<Role> roles = user.getUserRoles().stream().map(userRole-> userRole.getRole()).collect(Collectors.toList());
		model.addAttribute("role", roles.get(0).getName());
	}
	
	@GetMapping("/quizzes/{quizId}/questions/page={pageNumber}")
	public String viewAddQuestionPage(@PathVariable int quizId, @PathVariable int pageNumber, Model model, Principal principal) {
		loadCommonData(model, principal);
		model.addAttribute("title", "Add Question");
		model.addAttribute("quizzesActive", "active");
		Page<Question> questionPage = this.questionService.getQuestionsByQuiz(quizId, pageNumber, 25, "content", "asc");
		model.addAttribute("questionPage", questionPage);
		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("totalPages", questionPage.getTotalPages());
		Quiz quiz = this.quizService.getQuizById(quizId);
		model.addAttribute("quiz", quiz);
		return "admin-template/questions";
	}
	
}
