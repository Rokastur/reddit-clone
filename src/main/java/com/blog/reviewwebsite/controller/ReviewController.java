package com.blog.reviewwebsite.controller;

import com.blog.reviewwebsite.entities.*;
import com.blog.reviewwebsite.services.CategoryService;
import com.blog.reviewwebsite.services.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService reviewService;
    private CategoryService categoryService;
    private ContentOrderMap orderMap;

    public ReviewController(ReviewService reviewService, CategoryService categoryService, ContentOrderMap orderMap) {
        this.reviewService = reviewService;
        this.categoryService = categoryService;
        this.orderMap = orderMap;
    }


    @GetMapping
    private String getReviews(@RequestParam(defaultValue = "0") int pageNumber, Model model, @RequestParam(defaultValue = "DEFAULT") OrderType reviewOrderType, @RequestParam(defaultValue = "0") Long categoryId) {

        Category category = categoryService.getOneById(categoryId);
        orderMap.mapReviewsByCategoryToOrderType(pageNumber, category);
        Page<Review> reviews = orderMap.reviewsByOrderType.get(reviewOrderType);
        int pageCount = reviewService.getAllNotHiddenReviewsByCategory(pageNumber, category).getTotalPages();

        model.addAttribute("pageCount", pageCount);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("hasNextPage", reviews.hasNext());
        model.addAttribute("reviews", reviews.getContent());

        return "reviews";
    }

    @GetMapping("/review/{id}")
    private String getReview(Model model, @AuthenticationPrincipal User user, @PathVariable Long id, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "DEFAULT") OrderType commentOrderType) {

        Review review = reviewService.getReview(id);
        orderMap.mapCommentsByReviewToOrderType(pageNumber, review);
        Page<Comment> comments = orderMap.commentsByOrderType.get(commentOrderType);

        model.addAttribute("review", review);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("user", user);
        model.addAttribute("commentCount", comments.getTotalElements());
        model.addAttribute("comments", comments.getContent());
        model.addAttribute("newComment", new Comment());
        model.addAttribute("newScore", new Score());
        model.addAttribute("score", review.getTotalScore());
        model.addAttribute("newCommentScore", new CommentScore());

        return "review";
    }

    @PostMapping("/submit")
    private String form(@Valid @ModelAttribute Review review, BindingResult result, Model model, @AuthenticationPrincipal User user, @RequestParam(defaultValue = "0") Long categoryId) {
        if (result.hasErrors()) {
            return "form";
        } else {
            Category category = categoryService.getOneById(categoryId);
            Review newReview = reviewService.updateOrSaveReview(review, user, category);
            model.addAttribute("review", newReview);
            return "redirect:/reviews/?categoryId=" + categoryId;
        }
    }

    @GetMapping("/form")
    private String createReview(Model model, @AuthenticationPrincipal User user, @RequestParam(defaultValue = "0") Long categoryId) {
        Review review = new Review(user.getUsername());
        model.addAttribute("review", review);
        model.addAttribute("categoryId", categoryId);
        return "form";
    }

    @GetMapping("/byReviewer/{reviewer}")
    private String getReviewsByReviewer(@RequestParam(defaultValue = "0") int pageNumber, @PathVariable String reviewer, Model model) {
        Page<Review> reviews = reviewService.getAllReviewsByReviewer(pageNumber, reviewer);
        model.addAttribute("reviews", reviews.getContent());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("hasNext", reviews.hasNext());
        return "reviews";
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

}
