package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.entities.Review;
import com.blog.reviewwebsite.entities.Score;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.repositories.ReviewRepository;
import com.blog.reviewwebsite.repositories.ScoreRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ScoreService {

    private ScoreRepository scoreRepository;
    private ReviewRepository reviewRepository;

    public ScoreService(ScoreRepository scoreRepository, ReviewRepository reviewRepository) {
        this.scoreRepository = scoreRepository;
        this.reviewRepository = reviewRepository;
    }

    public void upvoteReview(Long id, User user, Score score) {
        Review review = reviewRepository.getOne(id);

        if (userAlreadyUpvotedReview(review, user)) {
            Score existingVote = scoreRepository.findOneByReviewAndUser(review, user);
            scoreRepository.delete(existingVote);
            return;
        }
        if (userAlreadyDownvotedReview(review, user)) {
            Score existingVote = scoreRepository.findOneByReviewAndUser(review, user);
            scoreRepository.delete(existingVote);
        }
        score.setReview(review);
        score.setUser(user);
        score.setUpvoted(true);
        scoreRepository.save(score);

    }

    public void downvoteReview(Long id, User user, Score score) {
        Review review = reviewRepository.getOne(id);

        if (userAlreadyDownvotedReview(review, user)) {
            Score existingVote = scoreRepository.findOneByReviewAndUser(review, user);
            scoreRepository.delete(existingVote);
            return;
        }
        if (userAlreadyUpvotedReview(review, user)) {
            Score existingVote = scoreRepository.findOneByReviewAndUser(review, user);
            scoreRepository.delete(existingVote);
        }
        score.setReview(review);
        score.setUser(user);
        score.setDownvoted(true);
        scoreRepository.save(score);

    }

    public Boolean userAlreadyUpvotedReview(Review review, User user) {
        return scoreRepository.existsByReviewAndUserAndUpvotedTrue(review, user);
    }

    public Boolean userAlreadyDownvotedReview(Review review, User user) {
        return scoreRepository.existsByReviewAndUserAndDownvotedTrue(review, user);
    }


    public Long calculateAndGetReviewVoteScore(Review review) {
        Set<Score> scores = scoreRepository.findAllByReview(review);
        Long upvoteCount = scores.stream()
                .filter(score -> score.getUpvoted() != null)
                .filter(Score::getUpvoted).count();
        Long downvoteCount = scores.stream()
                .filter((score -> score.getDownvoted() != null))
                .filter(Score::getDownvoted).count();
        return upvoteCount - downvoteCount;
    }
}
