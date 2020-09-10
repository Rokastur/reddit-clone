package com.blog.reviewwebsite.controller;

import com.blog.reviewwebsite.entities.Comment;
import com.blog.reviewwebsite.entities.Review;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.services.CommentService;
import com.blog.reviewwebsite.services.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService reviewService;
    private CommentService commentService;


    public ReviewController(ReviewService reviewService, CommentService commentService) {
        this.reviewService = reviewService;
        this.commentService = commentService;
    }

    @GetMapping
    private String getReviews(@RequestParam(defaultValue = "0") int pageNumber, Model model) {
        Page<Review> reviews = reviewService.getAllReviews(pageNumber);
        model.addAttribute("reviews", reviews.getContent());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("hasNextPage", reviews.hasNext());

        int pageCount = reviewService.getAllReviews(pageNumber).getTotalPages();
        model.addAttribute("pageCount", pageCount);

        return "reviews";
    }

    @GetMapping("/byReviewer/{reviewer}")
    private String getReviewsByReviewer(@RequestParam(defaultValue = "0") int pageNumber, @PathVariable String reviewer, Model model) {
        Page<Review> reviews = reviewService.getAllReviewsByReviewer(pageNumber, reviewer);
        model.addAttribute("reviews", reviews.getContent());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("hasNext", reviews.hasNext());
        return "reviews";
    }

    @GetMapping("/review/{id}")
    private String getReview(Model model, @AuthenticationPrincipal User user, @PathVariable Long id, @RequestParam(defaultValue = "0") int pageNumber) {
        Review review = reviewService.getReview(id);
        model.addAttribute("review", review);
        model.addAttribute("pageNumber", pageNumber);

        Page<Comment> comments = commentService.getAllCommentsByReview(pageNumber, review.getId());

        model.addAttribute("commentCount", comments.getTotalElements());

        model.addAttribute("comments", comments.getContent());
        model.addAttribute("newComment", new Comment());

        return "review";
    }

    @GetMapping("/form")
    private String createReview(Model model, @AuthenticationPrincipal User user) {
//        TODO: figure out if this is a good way to assign user to a review
        Review review = new Review(user.getUsername());
        model.addAttribute("review", review);
        return "form";
    }

    @GetMapping("/edit/{id}")
    private String editReview(Model model, @PathVariable Long id) {
        Review review = reviewService.getReview(id);
        model.addAttribute("review", review);
        return "form";
    }

    @GetMapping("/delete/{id}")
    private String deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "redirect:/reviews";
    }

    @PostMapping("/submit")
    private String form(@ModelAttribute Review review, Model model, @AuthenticationPrincipal User user) {
        Review newReview = reviewService.updateOrSaveReview(review, user);
        model.addAttribute("review", newReview);
        return "redirect:/reviews";
    }

}
