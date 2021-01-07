package com.blog.reviewwebsite.repositories;

import com.blog.reviewwebsite.entities.Comment;
import com.blog.reviewwebsite.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CommentRepository extends ContentRepository<Comment> {

    Set<Comment> findAllByReview(Review review);

    Page<Comment> findAllByReview(Review review, Pageable pageable);

    String commentCountQueryByReview = "SELECT * FROM comments WHERE review_id= :reviewId";
    String commentCountQueryByUser = "SELECT * FROM comments WHERE user_id= :userId";

    @Query(value = "SELECT * FROM comments WHERE review_id= :reviewId ORDER BY DATE DESC",
            countQuery = commentCountQueryByReview,
            nativeQuery = true)
    Page<Comment> findAllByReviewIdAndOrderByDateDesc(Long reviewId, Pageable pageable);

    @Query(value = "SELECT * FROM comments WHERE review_id= :reviewId ORDER BY DATE ASC",
            countQuery = commentCountQueryByReview,
            nativeQuery = true)
    Page<Comment> findAllByReviewIdAndOrderByDateAsc(Long reviewId, Pageable pageable);

    @Query(value = "SELECT c.* FROM comments c CROSS JOIN LATERAL (SELECT COUNT(*) FILTER(WHERE s.rating_type = 'UPVOTE') AS cnt_up, COUNT(*) FILTER(WHERE s.rating_type = 'DOWNVOTE') AS cnt_down FROM score INNER JOIN score s ON s.comment_id = c.id ) s WHERE review_id =:reviewId ORDER BY s.cnt_up - s.cnt_down DESC",
            countQuery = commentCountQueryByReview,
            nativeQuery = true)
    Page<Comment> findAllByReviewIdAndOrderByTotalScoreDesc(Long reviewId, Pageable pageable);

    @Query(value = "SELECT c.* FROM comments c CROSS JOIN LATERAL (SELECT COUNT(*) FILTER(WHERE s.rating_type = 'UPVOTE') AS cnt_up, COUNT(*) FILTER(WHERE s.rating_type = 'DOWNVOTE') AS cnt_down FROM score INNER JOIN score s ON s.comment_id = c.id ) s WHERE review_id =:reviewId ORDER BY s.cnt_up - s.cnt_down ASC",
            countQuery = commentCountQueryByReview,
            nativeQuery = true)
    Page<Comment> findAllByReviewIdAndOrderByTotalScoreAsc(Long reviewId, Pageable pageable);

    @Query(value = "SELECT * FROM comments WHERE user_id= :userId ORDER BY DATE DESC",
            countQuery = commentCountQueryByUser,
            nativeQuery = true)
    Page<Comment> findAllByUserAndOrderByDateDesc(Long userId, Pageable pageable);

    @Query(value = "SELECT * FROM comments WHERE user_id= :userId ORDER BY DATE ASC",
            countQuery = commentCountQueryByUser,
            nativeQuery = true)
    Page<Comment> findAllByUserAndOrderByDateAsc(Long userId, Pageable pageable);

    @Query(value = "SELECT c.* FROM comments c CROSS JOIN LATERAL (SELECT COUNT(*) FILTER(WHERE s.rating_type = 'UPVOTE') AS cnt_up, COUNT(*) FILTER(WHERE s.rating_type = 'DOWNVOTE') AS cnt_down FROM score INNER JOIN score s ON s.comment_id = c.id ) s WHERE user_id =:userId ORDER BY s.cnt_up - s.cnt_down DESC",
            countQuery = commentCountQueryByUser,
            nativeQuery = true)
    Page<Comment> findAllByUserAndOrderByTotalScoreDesc(Long userId, Pageable pageable);

    @Query(value = "SELECT c.* FROM comments c CROSS JOIN LATERAL (SELECT COUNT(*) FILTER(WHERE s.rating_type = 'UPVOTE') AS cnt_up, COUNT(*) FILTER(WHERE s.rating_type = 'DOWNVOTE') AS cnt_down FROM score INNER JOIN score s ON s.comment_id = c.id ) s WHERE user_id =:userId ORDER BY s.cnt_up - s.cnt_down ASC",
            countQuery = commentCountQueryByUser,
            nativeQuery = true)
    Page<Comment> findAllByUserAndOrderByTotalScoreAsc(Long userId, Pageable pageable);

}
