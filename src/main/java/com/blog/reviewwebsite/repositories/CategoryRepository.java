package com.blog.reviewwebsite.repositories;

import com.blog.reviewwebsite.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    String categoryCountQuery = "SELECT COUNT(*) FROM CATEGORY";

    @Query(value = "SELECT * FROM category WHERE category_id in(SELECT category_id FROM review GROUP BY category_id ORDER BY COUNT(*) DESC)",
            countQuery = categoryCountQuery,
            nativeQuery = true)
    Page<Category> findAllOrderByPostCountDesc(Pageable pageable);

    @Query(value = "SELECT * FROM category WHERE category_id in(SELECT category_id FROM review GROUP BY category_id ORDER BY COUNT(*) ASC)",
            countQuery = categoryCountQuery,
            nativeQuery = true)
    Page<Category> findAllOrderByPostCountAsc(Pageable pageable);

    @Query(value = "SELECT category_id FROM followed_categories GROUP BY category_id ORDER BY COUNT(*) DESC",
            countQuery = categoryCountQuery,
            nativeQuery = true)
    Page<Category> findAllOrderByFollowersCountDesc(Pageable pageable);

    @Query(value = "SELECT category_id FROM followed_categories GROUP BY category_id ORDER BY COUNT(*) ASC",
            countQuery = categoryCountQuery,
            nativeQuery = true)
    Page<Category> findAllOrderByFollowersCountAsc(Pageable pageable);

    @Query(value = "SELECT * FROM (SELECT DISTINCT ON (category_id) category_id, date FROM review ORDER BY category_id, date DESC) s ORDER BY date DESC",
            countQuery = categoryCountQuery,
            nativeQuery = true)
    Page<Category> findAllOrderByNewestPost(Pageable pageable);

    @Query(value = "SELECT * FROM (SELECT DISTINCT ON (category_id) category_id, date FROM review ORDER BY category_id, date ASC) s ORDER BY date ASC",
            countQuery = categoryCountQuery,
            nativeQuery = true)
    Page<Category> findAllOrderByOldestPost(Pageable pageable);

    @Query(value = "SELECT category_id FROM followed_categories WHERE user_id= :userId",
            countQuery = "SELECT COUNT(*) FROM followed_categories WHERE user_id =:userId",
            nativeQuery = true)
    Page<Category> findAllFollowedByUser(Long userId, Pageable pageable);

}
