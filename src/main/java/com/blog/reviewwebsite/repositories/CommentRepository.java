package com.blog.reviewwebsite.repositories;

import com.blog.reviewwebsite.entities.Comment;
import com.blog.reviewwebsite.entities.Review;
import com.blog.reviewwebsite.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT * FROM comments WHERE review_id= :reviewId ORDER BY DATE DESC", nativeQuery = true)
    Page<Comment> findAllByReviewIdAndOrderByDateDesc(Long reviewId, Pageable pageable);

    @Query(value = "SELECT * FROM comments WHERE review_id= :reviewId ORDER BY DATE ASC", nativeQuery = true)
    Page<Comment> findAllByReviewIdAndOrderByDateAsc(Long reviewId, Pageable pageable);

    @Query(value = "SELECT c.* FROM comments c JOIN comment_score s on c.comment_id = s.comment_id WHERE review_id =:reviewId GROUP BY c.comment_id ORDER BY COUNT(s.comment_id) DESC", nativeQuery = true)
    Page<Comment> findAllByReviewIdAndOrderByTotalScoreDesc(Long reviewId, Pageable pageable);

    @Query(value = "SELECT c.* FROM comments c JOIN comment_score s on c.comment_id = s.comment_id WHERE review_id =:reviewId GROUP BY c.comment_id ORDER BY COUNT(s.comment_id) DESC", nativeQuery = true)
    Page<Comment> findAllByReviewIdAndOrderByTotalScoreAsc(Long reviewId, Pageable pageable);

    Page<Comment> findAllByReview(Review review, Pageable pageable);

    Set<Comment> findAllByReview(Review review);

    Page<Comment> findAllByUser(User user, Pageable pageable);

    @Query(value = "SELECT * FROM comments WHERE user_id= :userId ORDER BY DATE DESC", nativeQuery = true)
    Page<Comment> findAllByUserAndOrderByDateDesc(Long userId, Pageable pageable);

    @Query(value = "SELECT * FROM comments WHERE user_id= :userId ORDER BY DATE ASC", nativeQuery = true)
    Page<Comment> findAllByUserAndOrderByDateAsc(Long userId, Pageable pageable);

    @Query(value = "SELECT c.* FROM comments c LEFT JOIN comment_score s on c.comment_id = s.comment_id WHERE user_id =:userId GROUP BY c.comment_id ORDER BY COUNT(s.comment_id) DESC", nativeQuery = true)
    Page<Comment> findAllByUserAndOrderByTotalScoreDesc(Long userId, Pageable pageable);

    @Query(value = "SELECT c.* FROM comments c LEFT JOIN comment_score s on c.comment_id = s.comment_id WHERE user_id =:userId GROUP BY c.comment_id ORDER BY COUNT(s.comment_id) ASC", nativeQuery = true)
    Page<Comment> findAllByUserAndOrderByTotalScoreAsc(Long userId, Pageable pageable);

}
