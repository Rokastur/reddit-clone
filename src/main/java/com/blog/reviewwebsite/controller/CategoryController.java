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

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private CategoryService categoryService;
    private UserService userService;

    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    private Map<OrderType, Page<Category>> categoriesByOrderType = new HashMap<>();

    public void assignCategoriesToOrderTypeMap(int pageNumber) {
        categoriesByOrderType.put(OrderType.DEFAULT, categoryService.getAllCategories(pageNumber));
        categoriesByOrderType.put(OrderType.POST_COUNT_DESC, categoryService.getAllCategoriesByPostCountDesc(pageNumber));
        categoriesByOrderType.put(OrderType.POST_COUNT_ASC, categoryService.getAllCategoriesByPostCountAsc(pageNumber));
        categoriesByOrderType.put(OrderType.FOLLOWER_COUNT_DESC, categoryService.getAllCategoriesByFollowersCountDesc(pageNumber));
        categoriesByOrderType.put(OrderType.FOLLOWER_COUNT_ASC, categoryService.getAllCategoriesByFollowersCountAsc(pageNumber));
        categoriesByOrderType.put(OrderType.POST_DATE_DESC, categoryService.getAllCategoriesByNewestPost(pageNumber));
        categoriesByOrderType.put(OrderType.POST_DATE_ASC, categoryService.getAllCategoriesByOldestPost(pageNumber));
    }

    public Page<Category> getAllCategoriesByOrderType(OrderType categoryOrderType) {
        return categoriesByOrderType.get(categoryOrderType);
    }

    @GetMapping("/all")
    public String getAllCategories(@RequestParam(defaultValue = "0") int pageNumber, Model model, @RequestParam(defaultValue = "DEFAULT") OrderType categoriesOrderType) {

        assignCategoriesToOrderTypeMap(pageNumber);

        Page<Category> categories = getAllCategoriesByOrderType(categoriesOrderType);

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

    @PostMapping("/follow/{id}")
    public String followCategory(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Long userId = user.getId();
        userService.followCategory(userId, id);
        return "redirect:/categories/all";
    }
}
