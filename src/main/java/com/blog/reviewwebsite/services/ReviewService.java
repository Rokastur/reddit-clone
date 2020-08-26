package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.entities.Review;
import com.blog.reviewwebsite.repositories.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Page<Review> getAllReviews(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAll(pageable);
    }

    public Page<Review> getAllReviewsByReviewer(int pageNumber, String reviewer) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByReviewer(reviewer, pageable);
    }

    public Review updateOrSaveReview(Review review) {
        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public Review getReview(Long id) {
        Review review = reviewRepository.getOne(id);
        return review;
    }
}
