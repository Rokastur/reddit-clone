package com.blog.reviewwebsite.controller;

import com.blog.reviewwebsite.entities.*;
import com.blog.reviewwebsite.repositories.UserRepository;
import com.blog.reviewwebsite.services.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private UserRepository userRepository;
    private ContentOrderMap orderMap;
    private ReviewService reviewService;
    private CommentService commentService;
    private CategoryService categoryService;
    private FileService fileService;

    public UserController(UserService userService, UserRepository userRepository, ContentOrderMap orderMap, ReviewService reviewService, CommentService commentService, CategoryService categoryService, FileService fileService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.orderMap = orderMap;
        this.reviewService = reviewService;
        this.commentService = commentService;
        this.categoryService = categoryService;
        this.fileService = fileService;
    }

    @GetMapping("/signup")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/submit")
    public String submitUser(User user) {
        if (userService.validNewUser(user) && (userService.passwordsMatch(user))) {
            userService.createUser(user);
            return "redirect:/login";
        } else {
            return "signup";
        }


        //TODO: this might be a temp fix only. Without this, multiple users with the same username can be created, despite having @unique annotation, but could not login with not unique username.
    }

    @GetMapping("/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/reviews";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> userList = userService.getUsers();
        model.addAttribute("users", userList);
        return "users";
    }

    @PostMapping("/incognito/toggle/{id}")
    public String toggleIncognito(@PathVariable Long id) {
        userService.toggleIncognito(id);
        return "redirect:/user/user/" + id;
    }

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable Long id, Model model, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "DEFAULT") OrderType reviewOrderType, @RequestParam(defaultValue = "DEFAULT") OrderType commentOrderType) throws UnsupportedEncodingException {

        User user = userService.getUser(id);

        model.addAttribute("incognito", user.isIncognito());
        orderMap.mapReviewsByUserToOrderType(pageNumber, user);
        Page<Review> reviews = orderMap.reviewsByOrderTypeAndUser.get(reviewOrderType);

        orderMap.mapCommentsByUserToOrderType(pageNumber, user);
        Page<Comment> comments = orderMap.commentsByOrderTypeAndUser.get(commentOrderType);

        long followedCategoriesCount = categoryService.getAllCategoriesUserFollows(user, pageNumber).getTotalElements();
        model.addAttribute("followedCategoriesCount", followedCategoriesCount);
        Page<Category> categories = categoryService.getAllCategoriesUserFollows(user, pageNumber);
        model.addAttribute("categories", categories);

        int pageCount = reviewService.findAllReviewsByReviewer(user.getUsername()).size();

        File file = fileService.getFileByUserId(user.getId());
        String image = fileService.retrieveImageEncodedInBase64(file);
        model.addAttribute("file", image);

        model.addAttribute("comments", comments);

        model.addAttribute("pageCount", pageCount);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("hasNextPage", reviews.hasNext());

        model.addAttribute("user", user);
        model.addAttribute("reviewCount", reviews.getSize());
        model.addAttribute("reviews", reviews);
        model.addAttribute("titles", reviews);
        return "user";

    }

    @GetMapping("/{username}")
    public String findUser(@RequestParam String username, Model model) throws UnsupportedEncodingException {
        User user = (User) userService.loadUserByUsername(username);
//TODO: deal with incorrect usernames. Currently redirects to log in screen
        return getUser(user.getId(), model, 0, OrderType.DEFAULT, OrderType.DEFAULT);

    }


}
