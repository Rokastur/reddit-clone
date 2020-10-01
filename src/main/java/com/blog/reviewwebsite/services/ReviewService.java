package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.entities.Review;
import com.blog.reviewwebsite.entities.Score;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.repositories.ReviewRepository;
import com.blog.reviewwebsite.repositories.ScoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;
    private ScoreRepository scoreRepository;

    public ReviewService(ReviewRepository reviewRepository, ScoreRepository scoreRepository) {
        this.reviewRepository = reviewRepository;
        this.scoreRepository = scoreRepository;
    }

    public Page<Review> getAllNotHiddenReviews(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalse(pageable);

    }

    public Page<Review> getAllNotHiddenReviewsByDateDesc(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseOrderByDateDesc(pageable);
    }

    public Page<Review> getAllNotHiddenReviewsByTotalScoreDesc(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseOrderByTotalScoreDesc(pageable);
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
