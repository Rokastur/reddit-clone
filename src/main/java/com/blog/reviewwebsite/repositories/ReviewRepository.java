package com.blog.reviewwebsite.repositories;

import com.blog.reviewwebsite.entities.Category;
import com.blog.reviewwebsite.entities.Review;
import com.blog.reviewwebsite.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByUser(User user, Pageable pageable);

    Set<Review> findAllByUser(User user);

    Page<Review> findAllByHiddenFalse(Pageable pageable);

    Page<Review> findAllByHiddenFalseAndCategory(Category category, Pageable pageable);

//    v ----------------- order types ----------------- v

    //ordering by score currently dont work with reviews who have no score assigned to them. possible work-arround for future - creating review also creates score entity, so ordering works
    @Query(value = "SELECT r.* FROM review r JOIN review_score s on r.review_id = s.review_id WHERE r.category_id =:categoryId AND hidden = false GROUP BY r.review_id ORDER BY COUNT(s.review_id) DESC", nativeQuery = true) //not sure if works
    Page<Review> findAllByHiddenFalseAndCategoryOrderByTotalScoreDesc(Long categoryId, Pageable pageable);

    @Query(value = "SELECT r.* FROM review r JOIN review_score s on r.review_id = s.review_id WHERE r.category_id =:categoryId AND hidden = false GROUP BY r.review_id ORDER BY COUNT(s.review_id) ASC", nativeQuery = true) //not sure if works
    Page<Review> findAllByHiddenFalseAndCategoryOrderByTotalScoreAsc(Long categoryId, Pageable pageable);

    @Query(value = "SELECT * FROM Review WHERE hidden = 'false' AND category_id= :categoryId ORDER BY DATE DESC", nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndCategoryOrderByDateDesc(Pageable pageable, Long categoryId);

    @Query(value = "SELECT * FROM Review WHERE hidden = 'false' AND category_id= :categoryId ORDER BY DATE ASC", nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndCategoryOrderByDateAsc(Pageable pageable, Long categoryId);

    @Query(value = "SELECT r.* FROM review r LEFT JOIN comments c ON r.review_id = c.review_id WHERE r.CATEGORY_id =:categoryId AND hidden = false GROUP BY r.review_id ORDER BY COUNT(c.review_id) DESC", nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndCategoryOrderByCommentCountDesc(Long categoryId, Pageable pageable);

    @Query(value = "SELECT r.* FROM review r LEFT JOIN comments c ON r.review_id = c.review_id WHERE r.CATEGORY_id =:categoryId AND hidden = false GROUP BY r.review_id ORDER BY COUNT(c.review_id) ASC", nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndCategoryOrderByCommentCountAsc(Long categoryId, Pageable pageable);

//    v ----------------- order types for single user reviews ----------------- v

    @Query(value = "SELECT r.* FROM review r JOIN review_score s on r.review_id = s.review_id WHERE id =:userId AND hidden = false GROUP BY r.review_id ORDER BY COUNT(s.review_id) DESC", nativeQuery = true) //not sure if works
    Page<Review> findAllByHiddenFalseAndUserOrderByTotalScoreDesc(Long userId, Pageable pageable);

    @Query(value = "SELECT r.* FROM review r JOIN review_score s on r.review_id = s.review_id WHERE id =:userId AND hidden = false GROUP BY r.review_id ORDER BY COUNT(s.review_id) ASC", nativeQuery = true) //not sure if works
    Page<Review> findAllByHiddenFalseAndUserOrderByTotalScoreAsc(Long userId, Pageable pageable);

    @Query(value = "SELECT * FROM review WHERE hidden = 'false' AND id =:userId ORDER BY DATE DESC", nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndUserOrderByDateDesc(Long userId, Pageable pageable);

    @Query(value = "SELECT * FROM review WHERE hidden = 'false' AND id =:userId ORDER BY DATE ASC", nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndUserOrderByDateAsc(Long userId, Pageable pageable);

    @Query(value = "SELECT r.* FROM review r LEFT JOIN comments c ON r.review_id = c.review_id WHERE id =:userId AND hidden = false GROUP BY r.review_id ORDER BY COUNT(c.review_id) DESC", nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndUserOrderByCommentCountDesc(Long userId, Pageable pageable);

    @Query(value = "SELECT r.* FROM review r LEFT JOIN comments c ON r.review_id = c.review_id WHERE id =:userId AND hidden = false GROUP BY r.review_id ORDER BY COUNT(c.review_id) ASC", nativeQuery = true)
    Page<Review> findAllByHiddenFalseAndUserOrderByCommentCountAsc(Long userId, Pageable pageable);


}
