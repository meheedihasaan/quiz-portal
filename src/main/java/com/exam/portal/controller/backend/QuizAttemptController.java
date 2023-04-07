package com.exam.portal.controller.backend;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/backend/quizzes")
public class QuizAttemptController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QuizService quizService;
	
	@Autowired
	private QuestionService questionService;

	public void loadCommonData(Model model, Principal principal) {
		String username = principal.getName();
		User user = userService.getUserByEmail(username);
		model.addAttribute("user", user);
		List<Role> roles = user.getUserRoles().stream().map(userRole-> userRole.getRole()).collect(Collectors.toList());
		model.addAttribute("role", roles.get(0).getName());
	}
	
	@PreAuthorize("hasRole('NORMAL')")
	@GetMapping("/{id}/{title}/quiz-rules")
	public String viewBeforeQuizPage(@PathVariable int id, Model model, Principal principal) {
		loadCommonData(model, principal);
		model.addAttribute("quizzesActive", "active");
		Quiz quiz = this.quizService.getQuizById(id);
		model.addAttribute("title", quiz.getTitle());
		model.addAttribute("quiz", quiz);
		return "admin-template/normal/quiz-rules";
	}
	
	@PreAuthorize("hasRole('NORMAL')")
	@GetMapping("/{id}/{title}/attempt")
	public String viewAttemptQuize(@PathVariable int id, Model model, Principal principal) {
		loadCommonData(model, principal);
		model.addAttribute("quizzesActive", "active");
		Quiz quiz = this.quizService.getQuizById(id);
		model.addAttribute("title", quiz.getTitle());
		model.addAttribute("quiz", quiz);
		List<Question> questions = this.questionService.getQuestionsByQuiz(id);
		model.addAttribute("questions", questions);
		return "admin-template/normal/attempt-quiz";
	}
	
}
