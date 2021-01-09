package com.blog.reviewwebsite.services;

import com.blog.reviewwebsite.controller.RatingType;
import com.blog.reviewwebsite.entities.Category;
import com.blog.reviewwebsite.entities.Review;
import com.blog.reviewwebsite.entities.Score;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.repositories.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;
    private CategoryService categoryService;
    private UserService userService;

    public ReviewService(ReviewRepository reviewRepository, CategoryService categoryService, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    public Review getReview(Long id) {
        return reviewRepository.getOne(id);
    }

    public void bookmarkReview(User user, Long id) {
        if (user == null) {
            return;
        }
        Review review = getReview(id);
        if (user.getBookmarkedReviews().contains(review)) {
            user.removeFromBookmarkedReviews(review);
        } else {
            user.addToBookmarkedReviews(review);
        }
        reviewRepository.save(review);
    }

    public Review updateReview(Review oldReview, User user, Long categoryId) {
        User dbUser = userService.getUser(user.getId());
        Category category = categoryService.getOneById(categoryId);
        Review review;
        if (oldReview.getId() != null) {
            review = getReview(oldReview.getId());
        } else {
            review = createNewReview(dbUser);
        }
        dbUser.addReview(review);
        category.addReview(review);
        review.setHidden(false);
        review.setText(oldReview.getText());
        review.setTitle(oldReview.getTitle());
        return reviewRepository.save(review);
    }

    //creates a new review, makes it upvoted by creator by default. updates relevant associations
    public Review createNewReview(User user) {
        Review review = new Review();
        Score score = new Score();
        score.setRatingType(RatingType.UPVOTE);
        user.addScore(score);
        review.addScore(score);
        return review;
    }

    public void deleteReview(Long id) {
        Review review = reviewRepository.getOne(id);
        review.setHidden(true);
        reviewRepository.save(review);
    }

    public Page<Review> findAllReviewsByReviewer(int pageNumber, User user) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByUser(user, pageable);
    }

    public Set<Review> getAllNotHiddenReviewsByCategory(Category category) {
        return reviewRepository.findAllByHiddenFalseAndCategory(category);
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

    public Page<Review> getAllNotHiddenReviewsByCategoryDateDesc(int pageNumber, Category category) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndCategoryOrderByDateDesc(pageable, category.getId());
    }

    public Page<Review> getAllNotHiddenReviewsByCategoryDateAsc(int pageNumber, Category category) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        return reviewRepository.findAllByHiddenFalseAndCategoryOrderByDateAsc(pageable, category.getId());
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

    public Page<Review> getAllReviewsByReviewer(int pageNumber, String username) {
        Pageable pageable = PageRequest.of(pageNumber, 4);
        User user = (User) userService.loadUserByUsername(username);
        return reviewRepository.findAllByUser(user, pageable);
    }

}
