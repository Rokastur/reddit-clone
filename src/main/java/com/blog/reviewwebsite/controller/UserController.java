package com.blog.reviewwebsite.controller;

import com.blog.reviewwebsite.entities.*;
import com.blog.reviewwebsite.services.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private ContentOrderMap orderMap;
    private ReviewService reviewService;
    private CategoryService categoryService;
    private FileService fileService;

    public UserController(UserService userService, ContentOrderMap orderMap, ReviewService reviewService, CategoryService categoryService, FileService fileService) {
        this.userService = userService;
        this.orderMap = orderMap;
        this.reviewService = reviewService;
        this.categoryService = categoryService;
        this.fileService = fileService;
    }

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable Long id, Model model, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "DEFAULT") OrderType reviewOrderType, @RequestParam(defaultValue = "DEFAULT") OrderType commentOrderType) throws UnsupportedEncodingException {

        User user = userService.getUser(id);

        orderMap.mapReviewsByUserToOrderType(pageNumber, user);
        orderMap.mapCommentsByUserToOrderType(pageNumber, user);

        Page<Review> reviews = orderMap.reviewsByOrderTypeAndUser.get(reviewOrderType);
        Page<Comment> comments = orderMap.commentsByOrderTypeAndUser.get(commentOrderType);
        Page<Category> categories = categoryService.getAllCategoriesUserFollows(user, pageNumber);

        long followedCategoriesCount = categoryService.getAllCategoriesUserFollows(user, pageNumber).getTotalElements();
        int pageCount = reviewService.findAllReviewsByReviewer(user).size();

        if (userService.userHasFile(user)) {
            File file = fileService.getFileByUserId(user.getId());
            String image = fileService.retrieveImageEncodedInBase64(file);
            model.addAttribute("file", image);
        }

        model.addAttribute("categories", categories);
        model.addAttribute("followedCategoriesCount", followedCategoriesCount);
        model.addAttribute("incognito", user.isIncognito());
        model.addAttribute("user", user);
        model.addAttribute("comments", comments);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("hasNextPage", reviews.hasNext());
        model.addAttribute("reviewCount", reviews.getSize());
        model.addAttribute("reviews", reviews);
        model.addAttribute("titles", reviews);

        return "user";
    }

    @PostMapping("/incognito/toggle/{id}")
    public String toggleIncognito(@PathVariable Long id) {
        userService.toggleIncognito(id);
        return "redirect:/user/user/" + id;
    }

    @PostMapping("{id}/submit/profile-description")
    public String submitProfileDescription(@ModelAttribute User user) {
        userService.updateUserDescription(user);
        return "redirect:/user/user/" + user.getId();
    }

    @PostMapping("/submit")
    public String submitUser(User user) {
        if (userService.validNewUser(user) && (userService.passwordsMatch(user))) {
            userService.createUser(user);
            return "redirect:/login";
        } else {
            return "signup";
        }
    }

    @GetMapping("/signup")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @GetMapping("/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/reviews";
    }

}
