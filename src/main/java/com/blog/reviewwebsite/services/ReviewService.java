package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.entities.Category;
import com.blog.reviewwebsite.entities.Review;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.repositories.ReviewRepository;
import com.blog.reviewwebsite.repositories.ScoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;
    private ScoreRepository scoreRepository;

    public ReviewService(ReviewRepository reviewRepository, ScoreRepository scoreRepository) {
        this.reviewRepository = reviewRepository;
        this.scoreRepository = scoreRepository;
    }

    public Page<Review> getAllNotHiddenByCommentCountDesc(int pageNumber, Category category) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndCategoryOrderByCommentCountDesc(category, pageable);
    }

    public Page<Review> getAllNotHiddenByCommentCountAsc(int pageNumber, Category category) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndCategoryOrderByCommentCountAsc(category, pageable);

    }

    public Page<Review> getAllNotHiddenReviewsByCategory(int pageNumber, Category category) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndCategory(category, pageable);

    }

    public Page<Review> getAllNotHiddenReviewsByCategoryDateDesc(int pageNumber, Long categoryId) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndCategoryOrderByDateDesc(pageable, categoryId);
    }

    public Page<Review> getAllNotHiddenReviewsByCategoryDateAsc(int pageNumber, Long categoryId) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndCategoryOrderByDateAsc(pageable, categoryId);
    }

    public Page<Review> getAllNotHiddenReviewsByTotalScoreDesc(int pageNumber, Category category) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndCategoryOrderByTotalScoreDesc(category, pageable);
    }

    public Page<Review> getAllNotHiddenReviewsByTotalScoreAsc(int pageNumber, Category category) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndCategoryOrderByTotalScoreAsc(category, pageable);
    }

    public List<Review> findAllReviewsByReviewer(String username) {
        return reviewRepository.findAllByUsername(username);
    }

    public Page<Review> getAllReviewsByReviewer(int pageNumber, String username) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByUsername(username, pageable);
    }

    public Review updateOrSaveReview(Review review, User user, Category category) {
//        sets currently authenticated user as a "owner" of the new or updated review
        review.setUser(user);
        review.setHidden(false);
        review.setCategory(category);
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
