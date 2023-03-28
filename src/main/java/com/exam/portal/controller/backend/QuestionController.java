package com.exam.portal.controller.backend;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.exam.portal.constsant.AppConstant;
import com.exam.portal.entity.Question;
import com.exam.portal.entity.Quiz;
import com.exam.portal.entity.Role;
import com.exam.portal.entity.User;
import com.exam.portal.helper.Message;
import com.exam.portal.service.QuestionService;
import com.exam.portal.service.QuizService;
import com.exam.portal.service.UserService;

import jakarta.validation.Valid;

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
	
	@GetMapping("/quizzes/{quizId}/{title}/questions/page={pageNumber}")
	public String viewQuestionsPage(@PathVariable int quizId, @PathVariable int pageNumber, Model model, Principal principal) {
		loadCommonData(model, principal);
		model.addAttribute("title", "Add Question");
		model.addAttribute("quizzesActive", "active");
		Page<Question> questionPage = this.questionService.getQuestionsByQuiz(quizId, pageNumber, AppConstant.QUESTION_PAGE_SIZE, "content", "asc");
		model.addAttribute("questionPage", questionPage);
		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("totalPages", questionPage.getTotalPages());
		Quiz quiz = this.quizService.getQuizById(quizId);
		model.addAttribute("quiz", quiz);
		return "admin-template/questions";
	}
	
	@GetMapping("/quizzes/{quizId}/{title}/questions/add")
	public String viewNewQuestionPage(@PathVariable int quizId, Model model, Principal principal) {
		loadCommonData(model, principal);
		model.addAttribute("title", "Add Question");
		model.addAttribute("quizzesActive", "active");
		Quiz quiz = this.quizService.getQuizById(quizId);
		model.addAttribute("quiz", quiz);
		model.addAttribute("question", new Question());
		return "admin-template/new-question";
	}
	
	@PostMapping("/quizzes/{quizId}/{title}/questions/add")
	public String createQuestion(
		@Valid @ModelAttribute Question question,
		BindingResult bindingResult,
		@PathVariable int quizId,
		@RequestParam(value="answer") String answer,
		RedirectAttributes redirectAttributes,
		Model model,
		Principal principal
	) {
		loadCommonData(model, principal);
		model.addAttribute("title","Add Question");
		model.addAttribute("quizzesActive", "active");
		Quiz quiz = this.quizService.getQuizById(quizId);
		try {
			if(quiz.getTotalQuestions() == this.questionService.countByQuiz(quiz)) {
				throw new Exception("This quiz has already required number of questions.");
			}
			
			if(bindingResult.hasErrors()) {
				model.addAttribute("question", question);
				model.addAttribute("quiz", quiz);
				return "admin-template/new-question";
			}
			
			if(answer.equals("A")) {
				answer = question.getOptionA();
			}
			else if(answer.equals("B")) {
				answer = question.getOptionB();
			}
			else if(answer.equals("C")) {
				answer = question.getOptionC();
			}
			else if(answer.equals("C")) {
				answer = question.getOptionD();
			}
			
			question.setAnswer(answer);
			question.setQuiz(quiz);
			this.questionService.createQuestion(question);
			redirectAttributes.addFlashAttribute("message", new Message("alert-primary", "Question is added successfully."));
			return "redirect:/backend/quizzes/"+quiz.getId()+"/"+quiz.getTitle()+"/questions/add";
		}
		catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", new Message("alert-primary", "Something went wrong! "+e.getMessage()));
			return "redirect:/backend/quizzes/"+quiz.getId()+"/"+quiz.getTitle()+"/questions/add";
		}
	}
	
}
