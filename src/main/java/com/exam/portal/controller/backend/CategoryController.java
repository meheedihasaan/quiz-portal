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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.exam.portal.entity.Category;
import com.exam.portal.entity.Role;
import com.exam.portal.entity.User;
import com.exam.portal.exception.AlreadyExistsException;
import com.exam.portal.helper.Message;
import com.exam.portal.service.CategoryService;
import com.exam.portal.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/backend/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private UserService userService;
	
	public void loadCommonData(Model model, Principal principal) {
		String email = principal.getName();
		User user = this.userService.getUserByEmail(email);
		model.addAttribute("user", user);
		List<Role> roles = user.getUserRoles().stream().map((userRole)-> userRole.getRole()).collect(Collectors.toList());
		model.addAttribute("role", roles.get(0).getName());
	}

	@GetMapping("/{pageNumber}") 
	public String viewCategoriesPage(@PathVariable int pageNumber, Model model, Principal principal) {
		loadCommonData(model, principal);
		model.addAttribute("title", "Categories");
		model.addAttribute("categoriesActive", "active");
		Page<Category> categoryPage = this.categoryService.getCategories(pageNumber, 5, "name", "asc");
		model.addAttribute("categoryPage", categoryPage);
		return "admin-template/categories";
	}
	
	@GetMapping("/new-category")
	public String viewNewCategoryPage(Model model, Principal principal) {
		loadCommonData(model, principal);
		model.addAttribute("title", "New Category");
		model.addAttribute("categoriesActive", "active");
		model.addAttribute("category", new Category());
		return "admin-template/new-category";
	}
	
	@PostMapping("/new-category")
	public String createCategory(
		@Valid @ModelAttribute Category category, BindingResult bindingResult,
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
				return "admin-template/new-category";
			}
			
			Category existingCategory = this.categoryService.getCategoryByName(category.getName());
			if(existingCategory != null) {
				throw new AlreadyExistsException("Category already exists with name "+category.getName()+".");	
			}
			else {
				this.categoryService.createCategory(category);
				redirectAttributes.addFlashAttribute("message", new Message("alert-primary", "Category is created successfully."));
				return "redirect:/backend/categories/new-category";
			}
		}
		catch (Exception e) {
			redirectAttributes.addFlashAttribute("message", new Message("alert-danger", e.getMessage()+" Please try again later."));
			return "redirect:/backend/categories/new-category";
		}
	}
	
}