package com.blog.reviewwebsite.controller;

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
    public String submitUpvote(@PathVariable Long id, @AuthenticationPrincipal User user) {
        scoreService.voteOnReview(id, user, RatingType.UPVOTE);
        return "redirect:/reviews/review/" + id;
    }

    @PostMapping("/submit/downvote/{id}")
    public String submitDownvote(@PathVariable Long id, @AuthenticationPrincipal User user) {
        scoreService.voteOnReview(id, user, RatingType.DOWNVOTE);
        return "redirect:/reviews/review/" + id;
    }

    @PostMapping("/submit/upvote/comment/{id}/{commentId}")
    public String submitCommentUpvote(@PathVariable Long id, @PathVariable Long commentId, @AuthenticationPrincipal User user) {
        scoreService.voteOnComment(commentId, user, RatingType.UPVOTE);
        return "redirect:/reviews/review/" + id;
    }

    @PostMapping("/submit/downvote/comment/{id}/{commentId}")
    public String submitCommentDownvote(@PathVariable Long id, @PathVariable Long commentId, @AuthenticationPrincipal User user) {
        scoreService.voteOnComment(commentId, user, RatingType.DOWNVOTE);
        return "redirect:/reviews/review/" + id;
    }
}
