package com.quiz.portal.controller.backend;

import java.security.Principal;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.quiz.portal.constsant.AppConstant;
import com.quiz.portal.entity.Category;
import com.quiz.portal.entity.Quiz;
import com.quiz.portal.entity.User;
import com.quiz.portal.helper.Message;
import com.quiz.portal.service.CategoryService;
import com.quiz.portal.service.QuizService;
import com.quiz.portal.service.UserService;

import jakarta.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/backend/quizzes")
public class QuizController {

	private final QuizService quizService;

	private final CategoryService categoryService;

	private final UserService userService;
	
	public void loadCommonData(Model model, Principal principal) {
		String email = principal.getName();
		User user = this.userService.getUserByEmail(email);
		model.addAttribute("user", user);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/page={pageNumber}")
	public String viewQuizzesPage(@PathVariable int pageNumber, Model model, Principal principal) {
		loadCommonData(model, principal);
		model.addAttribute("title", "Quizzes");
		model.addAttribute("quizzesActive", "active");
		Page<Quiz> quizPage = this.quizService.getQuizzes(pageNumber, AppConstant.QUIZ_PAGE_SIZE, "title", "asc");
		model.addAttribute("quizPage", quizPage);
		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("totalPages", quizPage.getTotalPages());
		return "admin-template/admin/quizzes";
	}
	
	@PreAuthorize("hasRole('NORMAL')")
	@GetMapping("/page-{pageNumber}")
	public String viewPublishedQuizzesPage(@PathVariable int pageNumber, Model model, Principal principal) {
		loadCommonData(model, principal);
		model.addAttribute("title", "Quizzes");
		model.addAttribute("quizzesActive", "active");
		Page<Quiz> quizPage = this.quizService.getPublishedQuizzes(pageNumber, AppConstant.QUIZ_PAGE_SIZE, "title", "asc");
		model.addAttribute("quizPage", quizPage);
		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("totalPages", quizPage.getTotalPages());
		return "admin-template/normal/published-quizzes";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/new")
	public String viewCreateQuizPage(Model model, Principal principal) {
		loadCommonData(model, principal);
		model.addAttribute("title", "New Quiz");
		model.addAttribute("quizzesActive", "active");
		model.addAttribute("quiz", new Quiz());
		List<Category> categories = this.categoryService.getCategoryList();
		model.addAttribute("categories", categories);
		return "admin-template/admin/new-quiz";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/new")
	public String createQuiz(
		@Valid @ModelAttribute Quiz quiz,
		BindingResult bindingResult,
		@RequestParam(value = "categoryId") int categoryId,
		@RequestParam(value = "isActive", defaultValue = "false") boolean isActive,
		RedirectAttributes redirectAttributes,
		Model model,
		Principal principal
	) {
		loadCommonData(model, principal);
		model.addAttribute("title", "New Quiz");
		model.addAttribute("quizzesActive", "active");
		try {
			if(bindingResult.hasErrors()) {
				model.addAttribute("quiz", quiz);
				List<Category> categories = this.categoryService.getCategoryList();
				model.addAttribute("categories", categories);
				return "admin-template/admin/new-quiz";
			}
			
			Category category = this.categoryService.getCategoryById(categoryId);
			quiz.setCategory(category);
			quiz.setActive(isActive);
			this.quizService.createQuiz(quiz);
			redirectAttributes.addFlashAttribute("message", new Message("alert-primary", "Quiz is created successfully"));
			return "redirect:/backend/quizzes/new";
		}
		catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", new Message("alert-danger", "Something went wrong! "+e.getMessage()));
			return "redirect:/backend/quizzes/new";
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}/edit")
	public String viewEditQuizPage(@PathVariable int id, Model model, Principal principal, RedirectAttributes redirectAttributes) {
		try {
			loadCommonData(model, principal);
			model.addAttribute("title", "Edit Quiz");
			model.addAttribute("quizzesActive", "active");
			Quiz quiz = this.quizService.getQuizById(id);
			model.addAttribute("quiz", quiz);
			List<Category> categories = this.categoryService.getCategoryList();
			model.addAttribute("categories", categories);
			return "admin-template/admin/edit-quiz";
		}
		catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", new Message("alert-danger", "Something went wrong! "+e.getMessage()));
			return "redirect:/backend/quizzes/page=1";
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/edit")
	public String editQuiz(
		@Valid @ModelAttribute Quiz quiz,
		BindingResult bindingResult,
		@RequestParam(value = "categoryId") int categoryId,
		@RequestParam(value = "isActive", defaultValue = "false") boolean isActive,
		RedirectAttributes redirectAttributes,
		Model model,
		Principal principal
	) {
		loadCommonData(model, principal);
		model.addAttribute("title", "Edit Quiz");
		model.addAttribute("quizzesActive", "active");
		try {
			if(bindingResult.hasErrors()) {
				Category category = this.categoryService.getCategoryById(categoryId);
				quiz.setCategory(category);
				model.addAttribute("quiz", quiz);
				List<Category> categories = this.categoryService.getCategoryList();
				model.addAttribute("categories", categories);
				return "admin-template/admin/edit-quiz";
			}
			
			Category category = this.categoryService.getCategoryById(categoryId);
			quiz.setCategory(category);
			quiz.setActive(isActive);
			this.quizService.updateQuiz(quiz.getId(), quiz);
			redirectAttributes.addFlashAttribute("message", new Message("alert-primary", "Quiz is updated successfully."));
			return "redirect:/backend/quizzes/page=0";
		}
		catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", new Message("alert-danger", "Something went wrong! "+e.getMessage()));
			return "redirect:/backend/quizzes/page=0";
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}/delete")
	public String deleteQuiz(@PathVariable int id, RedirectAttributes redirectAttributes) {
		try {
			this.quizService.deleteQuiz(id);
			redirectAttributes.addFlashAttribute("message", new Message("alert-success", "Quiz is deleted successfully."));
			return "redirect:/backend/quizzes/page=0";
		}
		catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", new Message("alert-danger", "Something went wrong! "+e.getMessage()));
			return "redirect:/backend/quizzes/page=0";
		}
	}
	
}