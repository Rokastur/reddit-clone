package com.blog.reviewwebsite.controller;

import com.blog.reviewwebsite.entities.Comment;
import com.blog.reviewwebsite.entities.Review;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.services.CommentService;
import com.blog.reviewwebsite.services.ReviewService;
import com.blog.reviewwebsite.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private CommentService commentService;
    private UserService userService;
    private ReviewService reviewService;

    public CommentController(CommentService commentService, UserService userService, ReviewService reviewService) {
        this.commentService = commentService;
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @PostMapping("/submit/{id}")
    public String submitComment(@ModelAttribute("newComment") Comment comment, @PathVariable Long id, @AuthenticationPrincipal User user, Model model) {
        commentService.saveOrUpdateComment(comment, user, id);
        return "redirect:/reviews/review/" + id;
    }
}
