package com.blog.reviewwebsite.controller;

import com.blog.reviewwebsite.entities.Review;
import com.blog.reviewwebsite.services.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    private ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    private String getReviews(@RequestParam(defaultValue = "0") int pageNumber, Model model) {
        Page<Review> reviews = reviewService.getAllReviews(pageNumber);
        model.addAttribute("reviews", reviews.getContent());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("hasNextPage", reviews.hasNext());
        return "reviews";
    }

}
