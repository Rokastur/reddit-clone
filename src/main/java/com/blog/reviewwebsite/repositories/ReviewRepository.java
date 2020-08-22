package com.blog.reviewwebsite.repositories;

import com.blog.reviewwebsite.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
