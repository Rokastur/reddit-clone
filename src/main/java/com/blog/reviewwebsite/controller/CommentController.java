package com.blog.reviewwebsite.controller;

import com.blog.reviewwebsite.entities.Comment;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.services.CommentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/submit/{id}")
    public String submitComment(@Valid @ModelAttribute("newComment") Comment comment, BindingResult result, @PathVariable Long id, @AuthenticationPrincipal User user) {
        if (!result.hasErrors()) {
            commentService.saveOrUpdateComment(comment, user, id);
        }
        return "redirect:/reviews/review/" + id;
    }

    @GetMapping("/delete/{id}/{commentId}")
    public String getDeleteComment(@PathVariable Long id, @PathVariable Long commentId) {
        commentService.deleteComment(id, commentId);
        return "redirect:/reviews/review/" + id;
    }
}
