package com.blog.reviewwebsite.controller;

import com.blog.reviewwebsite.entities.Category;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.services.CategoryService;
import com.blog.reviewwebsite.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService categoryService;
    private UserService userService;
    private ContentOrderMap orderMap;

    public CategoryController(CategoryService categoryService, UserService userService, ContentOrderMap orderMap) {
        this.categoryService = categoryService;
        this.userService = userService;
        this.orderMap = orderMap;
    }

    @GetMapping("/all")
    public String getAllCategories(@RequestParam(defaultValue = "0") int pageNumber, Model model, @RequestParam(defaultValue = "DEFAULT") OrderType categoriesOrderType) {
        orderMap.mapCategoriesToOrderType(pageNumber);
        Page<Category> categories = orderMap.categoriesByOrderType.get(categoriesOrderType);
        int pageCount = categoryService.getAllCategories(pageNumber).getTotalPages();
        model.addAttribute("categories", categories.getContent());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("hasNextPage", categories.hasNext());
        model.addAttribute("pageCount", pageCount);
        return "categories";
    }

    @PostMapping("/new/submit")
    public String submitNewCategory(@ModelAttribute Category category, @AuthenticationPrincipal User user) {
        categoryService.updateOrSaveCategory(category, user);
        return "redirect:/categories/all";
    }

    @PostMapping("/follow/{id}")
    public String followCategory(@PathVariable Long id, @AuthenticationPrincipal User user) {
        userService.followCategory(user.getId(), id);
        return "redirect:/categories/all";
    }

    @GetMapping("/new")
    public String createNewCategory(Model model) {
        model.addAttribute("category", new Category());
        return "categoryForm";
    }
}
