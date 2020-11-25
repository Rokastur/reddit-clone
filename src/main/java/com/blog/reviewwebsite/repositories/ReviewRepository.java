package com.blog.reviewwebsite.repositories;

import com.blog.reviewwebsite.entities.Category;
import com.blog.reviewwebsite.entities.Review;
import com.blog.reviewwebsite.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByUsername(String username, Pageable pageable);

    Page<Review> findAllByHiddenFalse(Pageable pageable);

    List<Review> findAllByUsername(String username);

    Page<Review> findAllByHiddenFalseAndCategory(Category category, Pageable pageable);

//    v ----------------- order types ----------------- v

    Page<Review> findAllByHiddenFalseAndCategoryOrderByTotalScoreDesc(Category category, Pageable pageable);

    Page<Review> findAllByHiddenFalseAndCategoryOrderByTotalScoreAsc(Category category, Pageable pageable);

    @Query(value = "SELECT * FROM Review WHERE hidden = 'false' AND category_id= :categoryId ORDER BY DATE DESC", nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndCategoryOrderByDateDesc(Pageable pageable, Long categoryId);

    @Query(value = "SELECT * FROM Review WHERE hidden = 'false' AND category_id= :categoryId ORDER BY DATE ASC", nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndCategoryOrderByDateAsc(Pageable pageable, Long categoryId);

    Page<Review> findAllByHiddenFalseAndCategoryOrderByCommentCountDesc(Category category, Pageable pageable);

    Page<Review> findAllByHiddenFalseAndCategoryOrderByCommentCountAsc(Category category, Pageable pageable);

//    v ----------------- order types for single user reviews ----------------- v

    Page<Review> findAllByHiddenFalseAndUserOrderByTotalScoreDesc(User user, Pageable pageable);

    Page<Review> findAllByHiddenFalseAndUserOrderByTotalScoreAsc(User user, Pageable pageable);

    @Query(value = "SELECT * FROM review WHERE hidden = 'false' AND username = :username ORDER BY DATE DESC", nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndUserOrderByDateDesc(String username, Pageable pageable);

    @Query(value = "SELECT * FROM review WHERE hidden = 'false' AND username = :username ORDER BY DATE ASC", nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndUserOrderByDateAsc(String username, Pageable pageable);

    Page<Review> findAllByHiddenFalseAndUserOrderByCommentCountDesc(User user, Pageable pageable);

    Page<Review> findAllByHiddenFalseAndUserOrderByCommentCountAsc(User user, Pageable pageable);


}
