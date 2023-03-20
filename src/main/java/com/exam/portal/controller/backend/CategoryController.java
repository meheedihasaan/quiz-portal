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

import com.exam.portal.entity.Category;
import com.exam.portal.entity.Role;
import com.exam.portal.entity.User;
import com.exam.portal.service.CategoryService;
import com.exam.portal.service.UserService;

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
	
}
