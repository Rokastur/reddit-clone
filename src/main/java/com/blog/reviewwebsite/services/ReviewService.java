package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.entities.Review;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.repositories.CommentRepository;
import com.blog.reviewwebsite.repositories.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Page<Review> getAllNotHiddenReviews(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalse(pageable);
    }

    public List<Review> findAllReviewsByReviewer(String reviewer) {
        return reviewRepository.findAllByReviewer(reviewer);
    }

    public Page<Review> getAllReviewsByReviewer(int pageNumber, String reviewer) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByReviewer(reviewer, pageable);
    }

    public Review updateOrSaveReview(Review review, User user) {
//        sets currently authenticated user as a "owner" of the new or updated review
        review.setUser(user);
        review.setHidden(false);
        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        Review review = reviewRepository.getOne(id);
        review.setHidden(true);
        reviewRepository.save(review);
        //reviewRepository.deleteById(id);
    }

    public Review getReview(Long id) {
        Review review = reviewRepository.getOne(id);
        return review;
    }
}
