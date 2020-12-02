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
    private CategoryService categoryService;

    public ReviewService(ReviewRepository reviewRepository, ScoreRepository scoreRepository, CategoryService categoryService) {
        this.reviewRepository = reviewRepository;
        this.scoreRepository = scoreRepository;
        this.categoryService = categoryService;
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

    public Page<Review> getAllNotHiddenByUserAndCommentCountDesc(int pageNumber, User user) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndUserOrderByCommentCountDesc(user, pageable);
    }

    public Page<Review> getAllNotHiddenByUserAndCommentCountAsc(int pageNumber, User user) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndUserOrderByCommentCountAsc(user, pageable);
    }

    public Page<Review> getAllNotHiddenByUserAndScoreDesc(int pageNumber, User user) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndUserOrderByTotalScoreDesc(user, pageable);
    }

    public Page<Review> getAllNotHiddenByUserAndScoreAsc(int pageNumber, User user) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndUserOrderByTotalScoreAsc(user, pageable);
    }

    public Page<Review> getAllNotHiddenByUserAndDateDesc(int pageNumber, User user) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndUserOrderByDateDesc(user.getUsername(), pageable);
    }

    public Page<Review> getAllNotHiddenByUserAndDateAsc(int pageNumber, User user) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndUserOrderByDateAsc(user.getUsername(), pageable);
    }

    public List<Review> findAllReviewsByReviewer(String username) {
        return reviewRepository.findAllByUsername(username);
    }

    public Page<Review> getAllReviewsByReviewer(int pageNumber, String username) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByUsername(username, pageable);
    }

    public Review updateReview(Review oldReview, User user, Long categoryId) {
        Review review;
        if (oldReview.getId() == null) {
            review = new Review();
        } else {
            review = reviewRepository.getOne(oldReview.getId());
        }
        review.setUser(user);
        review.setHidden(false);
        review.setUsername(user.getUsername());
        review.setCategory(categoryService.getOneById(categoryId));
        review.setText(oldReview.getText());
        review.setTitle(oldReview.getTitle());
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
