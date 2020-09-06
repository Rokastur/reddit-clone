package com.blog.reviewwebsite.repositories;

import com.blog.reviewwebsite.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findAllByReviewer(String reviewer, Pageable pageable);

    List<Review> findAllByReviewer(String reviewer);
}
