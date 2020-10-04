package com.blog.reviewwebsite.controller;

import com.blog.reviewwebsite.entities.Category;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.services.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public String getAllCategories(@RequestParam(defaultValue = "0") int pageNumber, Model model) {
        Page<Category> categories = categoryService.getAllCategories(pageNumber);
        model.addAttribute("categories", categories.getContent());

        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("hasNextPage", categories.hasNext());

        int pageCount = categoryService.getAllCategories(pageNumber).getTotalPages();
        model.addAttribute("pageCount", pageCount);
        return "categories";

    }

    @GetMapping("/new")
    public String createNewCategory(@AuthenticationPrincipal User user, Model model) {
        Category category = new Category(user.getUsername());
        model.addAttribute("category", category);
        return "categoryForm";
    }

    @PostMapping("/new/submit")
    public String submitNewCategory(@ModelAttribute Category category, @AuthenticationPrincipal User user, Model model) {
        Category newCat = categoryService.updateOrSaveCategory(category, user);
        model.addAttribute("category", newCat);
        return "redirect:/categories/all";
    }
}
