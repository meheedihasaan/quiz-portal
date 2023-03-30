package com.exam.portal.controller.backend;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.exam.portal.constsant.AppConstant;
import com.exam.portal.entity.Category;
import com.exam.portal.entity.Quiz;
import com.exam.portal.entity.Role;
import com.exam.portal.entity.User;
import com.exam.portal.helper.Message;
import com.exam.portal.service.CategoryService;
import com.exam.portal.service.QuizService;
import com.exam.portal.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/backend/quizzes")
public class QuizController {
	
	@Autowired 
	private QuizService quizService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private UserService userService;
	
	public void loadCommonData(Model model, Principal principal) {
		String email = principal.getName();
		User user = this.userService.getUserByEmail(email);
		model.addAttribute("user", user);
		List<Role> roles = user.getUserRoles().stream().map(userRole-> userRole.getRole()).collect(Collectors.toList());
		model.addAttribute("role", roles.get(0).getName());
	}

	@GetMapping("/page={pageNumber}")
	public String viewQuizzesPage(@PathVariable int pageNumber, Model model, Principal principal) {
		loadCommonData(model, principal);
		model.addAttribute("title", "Quizzes");
		model.addAttribute("quizzesActive", "active");
		Page<Quiz> quizPage = this.quizService.getQuizzes(pageNumber, AppConstant.QUIZ_PAGE_SIZE, "title", "asc");
		model.addAttribute("quizPage", quizPage);
		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("totalPages", quizPage.getTotalPages());
		return "admin-template/quizzes";
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
		return "admin-template/new-quiz";
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
				return "admin-template/new-quiz";
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
			return "admin-template/edit-quiz";
		}
		catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", new Message("alert-danger", "Something went wrong! "+e.getMessage()));
			return "redirect:/backend/quizzes/page=1";
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/{id}/edit")
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
				return "admin-template/edit-quiz";
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
