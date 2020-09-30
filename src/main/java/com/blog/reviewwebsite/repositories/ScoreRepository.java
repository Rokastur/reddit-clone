package com.blog.reviewwebsite.repositories;

import com.blog.reviewwebsite.entities.Review;
import com.blog.reviewwebsite.entities.Score;
import com.blog.reviewwebsite.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    Set<Score> findAllByReview(Review review);

    Boolean existsByReviewAndUser(Review review, User user);

    Boolean existsByReviewAndUserAndUpvotedTrue(Review review, User user);

    Boolean existsByReviewAndUserAndDownvotedTrue(Review review, User user);

    Score findOneByReviewAndUser(Review review, User user);

    Set<Score> findAllByReviewAndUpvotedTrue(Review review);

    Set<Score> findAllByReviewAndDownvotedTrue(Review review);

}
