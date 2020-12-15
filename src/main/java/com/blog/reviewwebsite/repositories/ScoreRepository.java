package com.blog.reviewwebsite.repositories;

import com.blog.reviewwebsite.entities.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query(value = "SELECT * FROM score JOIN review_score ON score.id = review_score.score_id WHERE user_id =:userId AND review_id =:reviewId", nativeQuery = true)
    Score getOneByUserAndReview(Long userId, Long reviewId);

    @Query(value = "SELECT COUNT(*) FROM score JOIN review_score ON score.id = review_score.score_id WHERE rating_type = 'UPVOTE' AND review_id =:reviewId", nativeQuery = true)
    long getUpvoteCountByReview(Long reviewId);

    @Query(value = "SELECT COUNT(*) FROM score JOIN review_score ON score.id = review_score.score_id WHERE rating_type = 'DOWNVOTE' AND review_id =:reviewId", nativeQuery = true)
    long getDownvoteCountByReview(Long reviewId);

    @Query(value = "SELECT * FROM score JOIN comment_score ON score.id = comment_score.score_id WHERE user_id =:userId and comment_id =:commentId", nativeQuery = true)
    Score getOneByUserAndComment(Long userId, Long commentId);

    @Query(value = "SELECT COUNT(*) FROM score JOIN comment_score ON score.id = comment_score.score_id WHERE rating_type = 'UPVOTE' AND comment_id =:commentId", nativeQuery = true)
    long getUpvoteCountByComment(Long commentId);

    @Query(value = "SELECT COUNT(*) FROM score JOIN comment_score ON score.id = comment_score.score_id WHERE rating_type = 'DOWNVOTE' AND comment_id =:commentId", nativeQuery = true)
    long getDownvoteCountByComment(Long commentId);

    @Query(value = "SELECT COUNT(*) FROM score JOIN review_score ON score.id = review_score.score_id WHERE rating_type = 'UPVOTE' AND review_id IN (SELECT review_id FROM review WHERE id =:userId)", nativeQuery = true)
    long getHowManyTimesThisUsersPostsHaveBeenUpvoted(Long userId);

    @Query(value = "SELECT COUNT(*) FROM score JOIN comment_score ON score.id = comment_score.score_id WHERE rating_type = 'UPVOTE' AND comment_id IN (SELECT comment_id FROM comments WHERE user_id =:userId)", nativeQuery = true)
    long getHowManyTimesThisUsersCommentsHaveBeenUpvoted(Long userId);


}
