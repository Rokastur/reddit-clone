package com.blog.reviewwebsite.controller;

import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.repositories.UserRepository;
import com.blog.reviewwebsite.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private UserRepository userRepository;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String createUser(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/submit")
    public String submitUser(User user) {
        if (userService.validNewUser(user)) {
            userService.updateOrSaveUser(user);
        }
        return "redirect:/login";

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
}
