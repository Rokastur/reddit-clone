package com.blog.reviewwebsite.controller;

import com.blog.reviewwebsite.entities.CommentScore;
import com.blog.reviewwebsite.entities.Review;
import com.blog.reviewwebsite.entities.Score;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.services.CommentScoreService;
import com.blog.reviewwebsite.services.ReviewService;
import com.blog.reviewwebsite.services.ScoreService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vote")
public class RatingController {

    private ScoreService scoreService;
    private CommentScoreService commentScoreService;

    public RatingController(ScoreService scoreService, CommentScoreService commentScoreService) {
        this.scoreService = scoreService;
        this.commentScoreService = commentScoreService;
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

    @PostMapping("/submit/upvote/comment/{id}/{commentId}")
    public String submitCommentUpvote(@ModelAttribute("newCommentScore") CommentScore score, @PathVariable Long id, @PathVariable Long commentId, @AuthenticationPrincipal User user) {
        commentScoreService.upvoteComment(commentId, user, score);
        return "redirect:/reviews/review/" + id;
    }

    @PostMapping("/submit/downvote/comment/{id}/{commentId}")
    public String submitCommentDownvote(@ModelAttribute("newCommentScore") CommentScore score, @PathVariable Long id, @PathVariable Long commentId, @AuthenticationPrincipal User user) {
        commentScoreService.downvoteComment(commentId, user, score);
        return "redirect:/reviews/review/" + id;
    }
}
