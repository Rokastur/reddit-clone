package com.blog.reviewwebsite.repositories;

import com.blog.reviewwebsite.entities.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query(value = "SELECT * FROM score WHERE user_id =:userId AND review_id =:reviewId", nativeQuery = true)
    Score getOneByUserAndReview(Long userId, Long reviewId);

    @Query(value = "SELECT (SELECT COUNT(*) FROM SCORE WHERE rating_type = 'UPVOTE' AND review_id =: reviewId) - (SELECT COUNT(*) FROM SCORE WHERE rating_type = 'DOWNVOTE' AND review_id =:reviewId) as score", nativeQuery = true)
    long getReviewScore(Long reviewId);

    @Query(value = "SELECT COUNT(*) FROM score WHERE rating_type = 'UPVOTE' AND review_id =:reviewId", nativeQuery = true)
    long getUpvoteCountByReview(Long reviewId);

    @Query(value = "SELECT COUNT(*) FROM score WHERE rating_type = 'DOWNVOTE' AND review_id =:reviewId", nativeQuery = true)
    long getDownvoteCountByReview(Long reviewId);

    @Query(value = "SELECT * FROM score WHERE user_id =:userId and comment_id =:commentId", nativeQuery = true)
    Score getOneByUserAndComment(Long userId, Long commentId);

    @Query(value = "SELECT (SELECT COUNT(*) FROM SCORE WHERE rating_type = 'UPVOTE' AND comment_id =:commentId) - (SELECT COUNT(*) FROM SCORE WHERE rating_type = 'DOWNVOTE' AND comment_id =:commentId) as score", nativeQuery = true)
    long getCommentScore(Long commentId);

    @Query(value = "SELECT COUNT(*) FROM score WHERE rating_type = 'UPVOTE' AND comment_id =:commentId", nativeQuery = true)
    long getUpvoteCountByComment(Long commentId);

    @Query(value = "SELECT COUNT(*) FROM score WHERE rating_type = 'DOWNVOTE' AND comment_id =:commentId", nativeQuery = true)
    long getDownvoteCountByComment(Long commentId);

    @Query(value = "SELECT COUNT(*) FROM score WHERE rating_type = 'UPVOTE' AND review_id IN (SELECT id FROM review WHERE user_id =:userId)", nativeQuery = true)
    long getHowManyTimesThisUsersPostsHaveBeenUpvoted(Long userId);

    @Query(value = "SELECT COUNT(*) FROM score WHERE rating_type = 'UPVOTE' AND comment_id IN (SELECT id FROM comments WHERE user_id =:userId)", nativeQuery = true)
    long getHowManyTimesThisUsersCommentsHaveBeenUpvoted(Long userId);


}
