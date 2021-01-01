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

    public long getCommentUpvoteCount(Comment comment) {
        return scoreRepository.getUpvoteCountByComment(comment.getId());
    }

    public long getCommentDownvoteCount(Comment comment) {
        return scoreRepository.getDownvoteCountByComment(comment.getId());
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

    public long howManyTimesThisUsersPostsHaveBeenUpvoted(User user) {
        return scoreRepository.getHowManyTimesThisUsersPostsHaveBeenUpvoted(user.getId());
    }

    public long howManyTimesThisUsersCommentsHaveBeenUpvoted(User user) {
        return scoreRepository.getHowManyTimesThisUsersCommentsHaveBeenUpvoted(user.getId());
    }


    //TODO: voting methods for review and comment look very similar. Look for a way to use one method only. Maybe inheritance.
    public void voteOnReview(Long reviewId, User user, RatingType ratingType) {
        Review review = reviewService.getReview(reviewId);
        User dbUser = userService.getUser(user.getId());
        Score score = updateReviewScoreIfExistsElseCreateNew(dbUser, review, ratingType);
        updateOrSaveVote(score);
    }

    public void voteOnComment(Long commentId, User user, RatingType ratingType) {
        Comment comment = commentService.getOneById(commentId);
        User dbUser = userService.getUser(user.getId());
        Score score = updateCommentScoreIfExistsElseCreateNew(dbUser, comment, ratingType);
        updateOrSaveVote(score);
    }

    public Score updateReviewScoreIfExistsElseCreateNew(User user, Review review, RatingType ratingType) {
        Score score;
        if (scoreRepository.getOneByUserAndReview(user.getId(), review.getId()) != null) {
            score = scoreRepository.getOneByUserAndReview(user.getId(), review.getId());
            updateRatingType(score, ratingType);
        } else {
            score = createNewReviewScore(ratingType, review, user);
        }
        return score;
    }

    public Score updateCommentScoreIfExistsElseCreateNew(User user, Comment comment, RatingType ratingType) {
        Score score;
        if (scoreRepository.getOneByUserAndComment(user.getId(), comment.getId()) != null) {
            score = scoreRepository.getOneByUserAndComment(user.getId(), comment.getId());
            updateRatingType(score, ratingType);
        } else {
            score = createNewCommentScore(ratingType, comment, user);
        }
        return score;
    }

    public Score createNewReviewScore(RatingType ratingType, Review review, User user) {
        Score score = new Score();
        updateRatingType(score, ratingType);
        review.addScore(score);
        score.setUser(user);
        return score;
    }

    public Score createNewCommentScore(RatingType ratingType, Comment comment, User user) {
        Score score = new Score();
        updateRatingType(score, ratingType);
        comment.addScore(score);
        score.setUser(user);
        return score;
    }

    public void updateRatingType(Score score, RatingType ratingType) {
        if (score.getRatingType().equals(ratingType)) {
            score.setRatingType(RatingType.NONE);
        } else {
            score.setRatingType(ratingType);
        }
    }

    public Score updateOrSaveVote(Score score) {
        return scoreRepository.save(score);
    }

}
