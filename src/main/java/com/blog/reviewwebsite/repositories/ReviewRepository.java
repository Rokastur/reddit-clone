package com.blog.reviewwebsite.repositories;

import com.blog.reviewwebsite.entities.Review;
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

    Page<Review> findAllByHiddenFalseOrderByTotalScoreDesc(Pageable pageable);

    @Query(value = "SELECT * FROM Review WHERE hidden = 'false' ORDER BY DATE DESC", nativeQuery = true)
    Page<Review> findAllByHiddenFalseOrderByDateDesc(Pageable pageable);

}
