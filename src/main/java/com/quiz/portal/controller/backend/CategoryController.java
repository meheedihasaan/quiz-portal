package com.quiz.portal.controller.backend;

import java.security.Principal;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.quiz.portal.constsant.AppConstant;
import com.quiz.portal.entity.Category;
import com.quiz.portal.entity.Quiz;
import com.quiz.portal.entity.User;
import com.quiz.portal.exception.AlreadyExistsException;
import com.quiz.portal.helper.Message;
import com.quiz.portal.service.CategoryService;
import com.quiz.portal.service.QuizService;
import com.quiz.portal.service.UserService;

import jakarta.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/backend/categories")
public class CategoryController {

	private final CategoryService categoryService;

	private final QuizService quizService;

	private final UserService userService;
	
	public void loadCommonData(Model model, Principal principal) {
		String email = principal.getName();
		User user = this.userService.getUserByEmail(email);
		model.addAttribute("user", user);
	}

	@GetMapping("/page={pageNumber}") 
	public String viewCategoriesPage(@PathVariable int pageNumber, Model model, Principal principal) {
		loadCommonData(model, principal);
		model.addAttribute("title", "Categories");
		model.addAttribute("categoriesActive", "active");
		Page<Category> categoryPage = this.categoryService.getCategories(pageNumber, AppConstant.CATEGORY_PAGE_SIZE, "name", "asc");
		model.addAttribute("categoryPage", categoryPage);
		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("totalPages", categoryPage.getTotalPages());
		return "admin-template/admin/categories";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}/{name}/quizzes/page={pageNumber}")
	public String viewCategorizedQuizPage(@PathVariable int id, @PathVariable int pageNumber, Model model, Principal principal) {
		loadCommonData(model, principal);
		model.addAttribute("categoriesActive", "active");
		Category category = this.categoryService.getCategoryById(id);
		model.addAttribute("category", category);
		model.addAttribute("title", category.getName());
		Page<Quiz> quizPage = this.quizService.getQuizzesByCategory(id, pageNumber, AppConstant.QUIZ_PAGE_SIZE, "title", "asc");
		model.addAttribute("quizPage", quizPage);
		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("totalPages", quizPage.getTotalPages());
		return "admin-template/admin/categorized-quizzes";
	}
	
	@PreAuthorize("hasRole('NORMAL')")
	@GetMapping("/{id}/{name}/quizzes/page-{pageNumber}")
	public String viewCategorizedPublishedQuizzes(@PathVariable int id, @PathVariable int pageNumber, Model model, Principal principal) {
		loadCommonData(model, principal);
		model.addAttribute("categoriesActive", "active");
		Category category = this.categoryService.getCategoryById(id);
		model.addAttribute("category", category);
		model.addAttribute("title", category.getName());
		Page<Quiz> quizPage = this.quizService.getPublishedQuizzesByCategory(id, pageNumber, AppConstant.QUIZ_PAGE_SIZE, "title", "asc");
		model.addAttribute("quizPage", quizPage);
		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("totalPages", quizPage.getTotalPages());
		return "admin-template/normal/categorized-published-quizzes";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/new")
	public String viewNewCategoryPage(Model model, Principal principal) {
		loadCommonData(model, principal);
		model.addAttribute("title", "New Category");
		model.addAttribute("categoriesActive", "active");
		model.addAttribute("category", new Category());
		return "admin-template/admin/new-category";
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/new")
	public String createCategory(
		@Valid @ModelAttribute Category category, 
		BindingResult bindingResult,
		RedirectAttributes redirectAttributes,
		Model model,
		Principal principal
	) {
		loadCommonData(model, principal);
		model.addAttribute("title", "New Category");
		model.addAttribute("categoriesActive", "active");
		try {
			if(bindingResult.hasErrors()) {
				model.addAttribute("category", category);
				return "admin-template/admin/new-category";
			}
			
			Category existingCategory = this.categoryService.getCategoryByName(category.getName());
			if(existingCategory != null) {
				throw new AlreadyExistsException("Category already exists with name "+category.getName()+".");	
			}
			else {
				this.categoryService.createCategory(category);
				redirectAttributes.addFlashAttribute("message", new Message("alert-primary", "Category is created successfully."));
				return "redirect:/backend/categories/new";
			}
		}
		catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", new Message("alert-danger", "Something went wrong! "+e.getMessage()));
			return "redirect:/backend/categories/new";
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}/edit")
    public String viewEditCategoryPage(@PathVariable int id, Model model, Principal principal, RedirectAttributes redirectAttributes) {
        try {
        	loadCommonData(model, principal);
            model.addAttribute("title", "Edit Category");
            model.addAttribute("categoriesActive", "active");
            Category category = this.categoryService.getCategoryById(id);
            model.addAttribute("category", category);
            return "admin-template/admin/edit-category";
        }
        catch (Exception e) {
        	redirectAttributes.addFlashAttribute("message", new Message("alert-danger", "Something went wrong! "+e.getMessage()));
			return "redirect:/backend/categories/page=0";  //When category is not found
        }
    }
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/edit")
	public String editCategory(
		@Valid @ModelAttribute Category category, 
		BindingResult bindingResult,
		RedirectAttributes redirectAttributes,
		Model model, 
		Principal principal
	) {
		loadCommonData(model, principal);
		model.addAttribute("title", "Edit Category");
		model.addAttribute("categoriesActive", "active");
		try {
			if(bindingResult.hasErrors()) {
				model.addAttribute("category", category);
				return "admin-template/admin/edit-category";
			}
			
			this.categoryService.updateCategory(category.getId(), category);
			redirectAttributes.addFlashAttribute("message", new Message("alert-primary", "Category is updated successfully."));
			return "redirect:/backend/categories/page=0";
		}
		catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", new Message("alert-danger", "Something went wrong! "+e.getMessage()));
			return "redirect:/backend/categories/page=0";
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}/{name}/delete")
	public String deleteCategory(@PathVariable int id, RedirectAttributes redirectAttributes) {
		try {
            this.categoryService.deleteCategory(id);
            redirectAttributes.addFlashAttribute("message", new Message("alert-success", "Category is deleted successfully."));
			return "redirect:/backend/categories/page=0";
        }
        catch (Exception e) {
        	redirectAttributes.addFlashAttribute("message", new Message("alert-danger", "Something went wrong! "+e.getMessage()));
			return "redirect:/backend/categories/page=0";
        }
	}
	
}