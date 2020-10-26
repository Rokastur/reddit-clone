package com.blog.reviewwebsite.controller;

import com.blog.reviewwebsite.entities.*;
import com.blog.reviewwebsite.services.CategoryService;
import com.blog.reviewwebsite.services.CommentService;
import com.blog.reviewwebsite.services.ReviewService;
import com.blog.reviewwebsite.services.ScoreService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService reviewService;
    private CommentService commentService;
    private ScoreService scoreService;
    private CategoryService categoryService;

    public ReviewController(ReviewService reviewService, CommentService commentService, ScoreService scoreService, CategoryService categoryService) {
        this.reviewService = reviewService;
        this.commentService = commentService;
        this.scoreService = scoreService;
        this.categoryService = categoryService;
    }

    private Map<OrderType, Page<Review>> reviewsByOrderType = new HashMap<>();

    @GetMapping
    private String getReviews(@RequestParam(defaultValue = "0") int pageNumber, Model model, @RequestParam(defaultValue = "DEFAULT") OrderType reviewOrderType, @RequestParam(defaultValue = "0") Long categoryId) {

        Category category = categoryService.getOneById(categoryId);
        model.addAttribute("categoryId", categoryId);

        int pageCount = reviewService.getAllNotHiddenReviewsByCategory(pageNumber, category).getTotalPages();
        model.addAttribute("pageCount", pageCount);

        assignReviewsToReviewsByOrderTypeMap(pageNumber, category);

        Page<Review> reviews = getReviewsByOrderType(reviewOrderType);

        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("hasNextPage", reviews.hasNext());
        model.addAttribute("reviews", reviews.getContent());
        return "reviews";
    }

    public void assignReviewsToReviewsByOrderTypeMap(int pageNumber, Category category) {
        reviewsByOrderType.put(OrderType.DEFAULT, reviewService.getAllNotHiddenReviewsByCategory(pageNumber, category));
        reviewsByOrderType.put(OrderType.COMMENT_COUNT_DESC, reviewService.getAllNotHiddenByCommentCountDesc(pageNumber, category));
        reviewsByOrderType.put(OrderType.COMMENT_COUNT_ASC, reviewService.getAllNotHiddenByCommentCountAsc(pageNumber, category));
        reviewsByOrderType.put(OrderType.DATE_DESC, reviewService.getAllNotHiddenReviewsByCategoryDateDesc(pageNumber, category.getId()));
        reviewsByOrderType.put(OrderType.DATE_ASC, reviewService.getAllNotHiddenReviewsByCategoryDateAsc(pageNumber, category.getId()));
        reviewsByOrderType.put(OrderType.SCORE_DESC, reviewService.getAllNotHiddenReviewsByTotalScoreDesc(pageNumber, category));
        reviewsByOrderType.put(OrderType.SCORE_ASC, reviewService.getAllNotHiddenReviewsByTotalScoreAsc(pageNumber, category));

    }

    public Page<Review> getReviewsByOrderType(OrderType reviewOrderType) {
        return reviewsByOrderType.get(reviewOrderType);
    }

    @GetMapping("/form")
    private String createReview(Model model, @AuthenticationPrincipal User user, @RequestParam(defaultValue = "0") Long categoryId) {
//        TODO: figure out if this is a good way to assign user to a review
        Review review = new Review(user.getUsername());
        model.addAttribute("review", review);
        model.addAttribute("categoryId", categoryId);
        return "form";
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

    @GetMapping("/byReviewer/{reviewer}")
    private String getReviewsByReviewer(@RequestParam(defaultValue = "0") int pageNumber, @PathVariable String reviewer, Model model) {
        Page<Review> reviews = reviewService.getAllReviewsByReviewer(pageNumber, reviewer);
        model.addAttribute("reviews", reviews.getContent());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("hasNext", reviews.hasNext());
        return "reviews";
    }

    @GetMapping("/review/{id}")
    private String getReview(Model model, @AuthenticationPrincipal User user, @PathVariable Long id, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "default") String commentOrderType) {
        Review review = reviewService.getReview(id);
        model.addAttribute("review", review);
        model.addAttribute("pageNumber", pageNumber);

        model.addAttribute("user", user);

        Page<Comment> comments = commentService.getAllCommentsByReview(pageNumber, review.getId());

        model.addAttribute("commentCount", comments.getTotalElements());


        String dateAsc = "dateAsc";
        String dateDesc = "dateDesc";
        String scoreDesc = "scoreDesc";
        String scoreAsc = "scoreAsc";

        model.addAttribute("dateAsc", dateAsc);
        model.addAttribute("dateDesc", dateDesc);
        model.addAttribute("scoreAsc", scoreAsc);
        model.addAttribute("scoreDesc", scoreDesc);


        if (commentOrderType.equals("dateAsc")) {
            comments = commentService.getAllCommentsByDateAsc(pageNumber, review.getId());
        } else if (commentOrderType.equals("dateDesc")) {
            comments = commentService.getAllCommentsByDateDesc(pageNumber, review.getId());
        } else if (commentOrderType.equals("scoreAsc")) {
            comments = commentService.getAllCommentsByScoreAsc(pageNumber, review.getId());
        } else if (commentOrderType.equals("scoreDesc")) {
            comments = commentService.getAllCommentsByScoreDesc(pageNumber, review.getId());
        }
        model.addAttribute("comments", comments.getContent());

        model.addAttribute("newComment", new Comment());

        model.addAttribute("newScore", new Score());
        model.addAttribute("score", review.getTotalScore());

        model.addAttribute("newCommentScore", new CommentScore());


        return "review";
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
