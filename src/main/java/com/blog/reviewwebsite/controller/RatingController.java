package com.blog.reviewwebsite.controller;

import com.blog.reviewwebsite.entities.Review;
import com.blog.reviewwebsite.entities.Score;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.services.ReviewService;
import com.blog.reviewwebsite.services.ScoreService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vote")
public class RatingController {

    private ScoreService scoreService;

    public RatingController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping("/submit/upvote/{id}")
    public String submitUpvote(@ModelAttribute("newScore") Score score, @PathVariable Long id, @AuthenticationPrincipal User user) {
        scoreService.upvoteReview(id, user, score);
        return "redirect:/reviews/review/" + id;
    }

    @PostMapping("/submit/downvote/{id}")
    public String submitDownvote(@ModelAttribute("newScore") Score score, @PathVariable Long id, @AuthenticationPrincipal User user) {
        scoreService.downvoteReview(id, user, score);
        return "redirect:/reviews/review/" + id;
    }

}
