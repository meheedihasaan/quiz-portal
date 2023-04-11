package com.exam.portal.controller.backend;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.exam.portal.entity.Question;
import com.exam.portal.entity.QuestionResponse;
import com.exam.portal.entity.Quiz;
import com.exam.portal.entity.QuizResult;
import com.exam.portal.entity.User;
import com.exam.portal.service.QuestionService;
import com.exam.portal.service.QuizResultService;
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
	
	@Autowired
	private QuizResultService quizResultService;

	public void loadCommonData(Model model, Principal principal) {
		String username = principal.getName();
		User user = userService.getUserByEmail(username);
		model.addAttribute("user", user);
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
	@GetMapping("/{id}/{title}/start")
	public String viewAttemptQuize(@PathVariable int id, Model model, Principal principal) {
		loadCommonData(model, principal);
		model.addAttribute("quizzesActive", "active");
		Quiz quiz = this.quizService.getQuizById(id);
		model.addAttribute("title", quiz.getTitle());
		model.addAttribute("quiz", quiz);
		List<Question> questions = this.questionService.getQuestionsByQuiz(id);
		model.addAttribute("questions", questions);
		
		QuestionResponse questionResponse = new QuestionResponse();
		questionResponse.setResponses(questions);
		model.addAttribute("questionResponse", questionResponse);
		return "admin-template/normal/start-quiz";
	}
	
	
	@PreAuthorize("hasRole('NORMAL')")
	@PostMapping("/{id}/{title}/result")
	public String evaluateQuiz(@PathVariable int id, @ModelAttribute("questionResponse") QuestionResponse questionResponse, Model model, Principal principal) {
		loadCommonData(model, principal);
		model.addAttribute("quizzesActive", "active");
		Quiz quiz = this.quizService.getQuizById(id);
		model.addAttribute("title", "Result -"+quiz.getTitle());
		model.addAttribute("quiz", quiz);
		
		try {
			int attemptedQuestions = quiz.getTotalQuestions();
			int correctAnswers = 0;
			List<Question> responses = questionResponse.getResponses();		
			for (Question response : responses) {
				Question question = this.questionService.getQuestionById(response.getId());
				
				if(response.getUserAnswer() == null) {
					attemptedQuestions--;
					continue;
				}
				
				if(response.getUserAnswer().trim().equals(question.getAnswer().trim())) {
					correctAnswers++;
				}
			}
			
			int obtainedMarks = correctAnswers * (quiz.getTotalMarks()/quiz.getTotalQuestions());
			double successRate = (double)correctAnswers/(double)attemptedQuestions;
			int accuracy = (int)Math.floor((successRate*100));
			
			QuizResult quizResult = new QuizResult();
			quizResult.setQuiz(quiz);
			quizResult.setUser(userService.getUserByEmail(principal.getName()));
			quizResult.setObtainedMarks(obtainedMarks);
			quizResult.setAttemptedQuestions(attemptedQuestions);
			quizResult.setCorrectAnswers(correctAnswers);
			quizResult.setAccuracy(accuracy);			
			QuizResult savedQuizResult = this.quizResultService.createQuizResult(quizResult);

			model.addAttribute("quizResult", savedQuizResult);
			return "admin-template/normal/result";
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/backend";
		}
	}
	
}
