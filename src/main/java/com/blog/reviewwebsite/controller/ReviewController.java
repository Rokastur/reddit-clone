package com.blog.reviewwebsite.controller;

import com.blog.reviewwebsite.entities.*;
import com.blog.reviewwebsite.services.CategoryService;
import com.blog.reviewwebsite.services.ReviewService;
import com.blog.reviewwebsite.services.ScoreService;
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
    private ScoreService scoreService;

    public ReviewController(ReviewService reviewService, CategoryService categoryService, ContentOrderMap orderMap, ScoreService scoreService) {
        this.reviewService = reviewService;
        this.categoryService = categoryService;
        this.orderMap = orderMap;
        this.scoreService = scoreService;
    }

    @GetMapping
    private String getReviews(@RequestParam(defaultValue = "0") int pageNumber, Model model, @RequestParam(defaultValue = "DEFAULT") OrderType reviewOrderType, @RequestParam(defaultValue = "0") Long categoryId) {
        Category category = categoryService.getOneById(categoryId);
        orderMap.mapReviewsByCategoryToOrderType(pageNumber, category);
        Page<Review> reviews = orderMap.reviewsByOrderType.get(reviewOrderType);
        int pageCount = reviewService.getAllNotHiddenReviewsByCategory(pageNumber, category).getTotalPages();
        scoreService.mapScoreToCategoryReviewsId(category);
        model.addAttribute("reviewScore", scoreService.getReviewScoreMap());
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
        scoreService.mapScoreToReviewCommentsId(review);
        long reviewScore = scoreService.getReviewScore(review);
        Page<Comment> comments = orderMap.commentsByOrderType.get(commentOrderType);

        model.addAttribute("commentScore", scoreService.getCommentScoreMap());
        model.addAttribute("review", review);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("user", user);
        model.addAttribute("commentCount", comments.getTotalElements());
        model.addAttribute("comments", comments.getContent());
        model.addAttribute("newComment", new Comment());
        model.addAttribute("score", reviewScore);
        return "review";
    }

    @PostMapping("/submit")
    private String form(@Valid @ModelAttribute Review review, BindingResult result, Model model, @AuthenticationPrincipal User user, @RequestParam(defaultValue = "0") Long categoryId) {
        if (result.hasErrors()) {
            return "form";
        } else {
            reviewService.updateReview(review, user, categoryId);
            model.addAttribute("review", review);
            return "redirect:/reviews/?categoryId=" + categoryId;
        }
    }

    @GetMapping("/form")
    private String createReview(Model model, @AuthenticationPrincipal User user, @RequestParam(defaultValue = "0") Long categoryId) {
        Review review = new Review();
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
        model.addAttribute("categoryId", review.getCategory().getId());
        return "form";
    }

    @GetMapping("/delete/{id}")
    private String deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return "redirect:/reviews";
    }

}
