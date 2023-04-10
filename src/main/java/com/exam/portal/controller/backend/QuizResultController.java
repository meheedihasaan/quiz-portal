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

import com.exam.portal.constsant.AppConstant;
import com.exam.portal.entity.QuizResult;
import com.exam.portal.entity.Role;
import com.exam.portal.entity.User;
import com.exam.portal.service.QuizResultService;
import com.exam.portal.service.UserService;

@Controller
@RequestMapping("/backend")
public class QuizResultController {
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private QuizResultService quizResultService;

	public void loadCommonData(Model model, Principal principal) {
		String username = principal.getName();
		User user = userService.getUserByEmail(username);
		model.addAttribute("user", user);
		List<Role> roles = user.getUserRoles().stream().map(userRole-> userRole.getRole()).collect(Collectors.toList());
		model.addAttribute("role", roles.get(0).getName());
	}
	
	@GetMapping("/attempted-quizzes/page={pageNumber}")
	public String viewAttemptedQuizzesPage(@PathVariable int pageNumber, Model model, Principal principal) {
		loadCommonData(model, principal);
		model.addAttribute("title", "Attempted Quizzes");
		model.addAttribute("attemptedQuizzesActive", "active");
		User user = this.userService.getUserByEmail(principal.getName());
		Page<QuizResult> quizResultPage = this.quizResultService.getQuizResultsByUser(user.getId(), pageNumber, AppConstant.QUIZ_RESULT_PAGE_SIZE, "date", "desc");
		model.addAttribute("quizResultPage", quizResultPage);
		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("totalPages", quizResultPage.getTotalPages());
		return "admin-template/normal/attempted-quizzes";
	}
	
	@GetMapping("/attempted-quizzes/{resultId}/{quizTitle}")
	public String viewSingleQuizResult(@PathVariable int resultId, Model model, Principal principal) {
		loadCommonData(model, principal);
		model.addAttribute("attemptedQuizzesActive", "active");
		QuizResult quizResult = this.quizResultService.getQuizResultById(resultId);
		model.addAttribute("title", "Result"+quizResult.getQuiz().getTitle());
		model.addAttribute("quizResult", quizResult);
		return "admin-template/normal/single-quiz-result";
	}
	
}
