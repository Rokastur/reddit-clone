package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.controller.RatingType;
import com.blog.reviewwebsite.entities.*;
import com.blog.reviewwebsite.repositories.ScoreRepository;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ScoreService {

    private UserService userService;
    private ReviewService reviewService;
    private CommentService commentService;
    private ScoreRepository scoreRepository;

    public ScoreService(UserService userService, ReviewService reviewService, CommentService commentService, ScoreRepository scoreRepository) {
        this.userService = userService;
        this.reviewService = reviewService;
        this.commentService = commentService;
        this.scoreRepository = scoreRepository;
    }

    @Getter
    private Map<Long, Long> commentScoreMap = new HashMap<>();

    @Getter
    private Map<Long, Long> reviewScoreMap = new HashMap<>();

    public long getReviewScore(Review review) {
        return getReviewUpvoteCount(review) - getReviewDownvoteCount(review);
    }

    public long getReviewUpvoteCount(Review review) {
        return scoreRepository.getUpvoteCountByReview(review.getId());
    }

    public long getReviewDownvoteCount(Review review) {
        return scoreRepository.getDownvoteCountByReview(review.getId());
    }

    public long getCommentScore(Comment comment) {
        return getCommentUpvoteCount(comment) - getCommentDownvoteCount(comment);
    }

    public void mapScoreToCategoryReviewsId(Category category) {
        Set<Review> reviews = reviewService.getAllNotHiddenReviewsByCategory(category);
        for (Review review : reviews) {
            reviewScoreMap.put(review.getId(), getReviewScore(review));
        }
    }

    public void mapScoreToReviewCommentsId(Review review) {
        Set<Comment> reviewComments = commentService.getAllCommentsByReview(review);
        for (Comment comment : reviewComments) {
            commentScoreMap.put(comment.getId(), getCommentScore(comment));
        }
    }

    public long getCommentUpvoteCount(Comment comment) {
        return scoreRepository.getUpvoteCountByComment(comment.getId());
    }

    public long getCommentDownvoteCount(Comment comment) {
        return scoreRepository.getDownvoteCountByComment(comment.getId());
    }

    public void voteOnReview(Long reviewId, User user, RatingType ratingType) {
        Score score;
        Review review = reviewService.getReview(reviewId);
        User dbUser = userService.getUser(user.getId());

        if (scoreRepository.getOneByUserAndReview(user.getId(), reviewId) != null) {
            score = scoreRepository.getOneByUserAndReview(user.getId(), reviewId);
            if (score.getRatingType().equals(ratingType)) {
                score.setRatingType(RatingType.NONE);
            } else {
                score.setRatingType(ratingType);
            }
        } else {
            score = new Score();
            score.setRatingType(ratingType);
            review.getReviewScore().add(score);
            dbUser.getScore().add(score);
            score.setUser(user);
        }
        updateOrSaveVote(score);
    }

    public void voteOnComment(Long commentId, User user, RatingType ratingType) {
        Score score;
        Comment comment = commentService.getOneById(commentId);
        User dbUser = userService.getUser(user.getId());

        if (scoreRepository.getOneByUserAndComment(user.getId(), commentId) != null) {
            score = scoreRepository.getOneByUserAndComment(user.getId(), commentId);
            if (score.getRatingType().equals(ratingType)) {
                score.setRatingType(RatingType.NONE);
            } else {
                score.setRatingType(ratingType);
            }
        } else {
            score = new Score();
            score.setRatingType(ratingType);
            comment.getCommentScore().add(score);
            dbUser.getScore().add(score);
            score.setUser(user);
        }
        updateOrSaveVote(score);
    }

    public Score updateOrSaveVote(Score score) {
        return scoreRepository.save(score);
    }

}
