package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.controller.RatingType;
import com.blog.reviewwebsite.entities.Category;
import com.blog.reviewwebsite.entities.Review;
import com.blog.reviewwebsite.entities.Score;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.repositories.ReviewRepository;
import com.blog.reviewwebsite.repositories.ScoreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;
    private ScoreService scoreService;
    private CategoryService categoryService;
    private UserService userService;

    public ReviewService(ReviewRepository reviewRepository, ScoreService scoreService, CategoryService categoryService, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.scoreService = scoreService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    public Page<Review> getAllNotHiddenByCommentCountDesc(int pageNumber, Category category) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndCategoryOrderByCommentCountDesc(category.getId(), pageable);
    }

    public Page<Review> getAllNotHiddenByCommentCountAsc(int pageNumber, Category category) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndCategoryOrderByCommentCountAsc(category.getId(), pageable);
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
        return reviewRepository.findAllByHiddenFalseAndCategoryOrderByTotalScoreDesc(category.getId(), pageable);
    }

    public Page<Review> getAllNotHiddenReviewsByTotalScoreAsc(int pageNumber, Category category) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndCategoryOrderByTotalScoreAsc(category.getId(), pageable);
    }

    public Page<Review> getAllNotHiddenByUserAndCommentCountDesc(int pageNumber, User user) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndUserOrderByCommentCountDesc(user.getId(), pageable);
    }

    public Page<Review> getAllNotHiddenByUserAndCommentCountAsc(int pageNumber, User user) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndUserOrderByCommentCountAsc(user.getId(), pageable);
    }

    public Page<Review> getAllNotHiddenByUserAndScoreDesc(int pageNumber, User user) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndUserOrderByTotalScoreDesc(user.getId(), pageable);
    }

    public Page<Review> getAllNotHiddenByUserAndScoreAsc(int pageNumber, User user) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndUserOrderByTotalScoreAsc(user.getId(), pageable);
    }

    public Page<Review> getAllNotHiddenByUserAndDateDesc(int pageNumber, User user) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndUserOrderByDateDesc(user.getId(), pageable);
    }

    public Page<Review> getAllNotHiddenByUserAndDateAsc(int pageNumber, User user) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndUserOrderByDateAsc(user.getId(), pageable);
    }

    public Set<Review> findAllReviewsByReviewer(User user) {
        return reviewRepository.findAllByUser(user);
    }

    public Page<Review> getAllReviewsByReviewer(int pageNumber, String username) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        User user = (User) userService.loadUserByUsername(username);
        return reviewRepository.findAllByUser(user, pageable);
    }

    public Review updateReview(Review oldReview, User user, Long categoryId) {
        Review review;
        if (oldReview.getId() == null) {
            review = new Review();
            Score score = new Score();
            score.setUser(user);
            score.setRatingType(RatingType.UPVOTE);
            review.getReviewScore().add(score);
        } else {
            review = reviewRepository.getOne(oldReview.getId());
        }
        review.setUser(user);
        review.setHidden(false);
        review.setCategory(categoryService.getOneById(categoryId));
        review.setText(oldReview.getText());
        review.setTitle(oldReview.getTitle());
        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        Review review = reviewRepository.getOne(id);
        review.setHidden(true);
        reviewRepository.save(review);
    }

    public Review getReview(Long id) {
        return reviewRepository.getOne(id);
    }
}
