package com.blog.reviewwebsite.repositories;

import com.blog.reviewwebsite.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByReviewId(Long reviewId, Pageable pageable);


    @Query(value = "SELECT * FROM comments WHERE review_id= :reviewId ORDER BY DATE DESC", nativeQuery = true)
    Page<Comment> findAllByReviewIdAndOrderByDateDesc(@Param("reviewId") Long reviewId, Pageable pageable);

    @Query(value = "SELECT * FROM comments WHERE review_id= :reviewId ORDER BY DATE ASC", nativeQuery = true)
    Page<Comment> findAllByReviewIdAndOrderByDateAsc(Long reviewId, Pageable pageable);

}
