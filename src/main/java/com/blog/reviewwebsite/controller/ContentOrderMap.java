package com.blog.reviewwebsite.controller;

import com.blog.reviewwebsite.entities.Category;
import com.blog.reviewwebsite.entities.Comment;
import com.blog.reviewwebsite.entities.Review;
import com.blog.reviewwebsite.entities.User;
import com.blog.reviewwebsite.services.CategoryService;
import com.blog.reviewwebsite.services.CommentService;
import com.blog.reviewwebsite.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ContentOrderMap {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CategoryService categoryService;

    public Map<OrderType, Page<Review>> reviewsByOrderType = new HashMap<>();
    public Map<OrderType, Page<Review>> reviewsByOrderTypeAndUser = new HashMap<>();
    public Map<OrderType, Page<Comment>> commentsByOrderType = new HashMap<>();
    public Map<OrderType, Page<Comment>> commentsByOrderTypeAndUser = new HashMap<>();
    public Map<OrderType, Page<Category>> categoriesByOrderType = new HashMap<>();

    public void mapReviewsByCategoryToOrderType(int pageNumber, Category category) {
        reviewsByOrderType.put(OrderType.DEFAULT, reviewService.getAllNotHiddenReviewsByCategory(pageNumber, category));
        reviewsByOrderType.put(OrderType.COMMENT_COUNT_DESC, reviewService.getAllNotHiddenByCommentCountDesc(pageNumber, category));
        reviewsByOrderType.put(OrderType.COMMENT_COUNT_ASC, reviewService.getAllNotHiddenByCommentCountAsc(pageNumber, category));
        reviewsByOrderType.put(OrderType.DATE_DESC, reviewService.getAllNotHiddenReviewsByCategoryDateDesc(pageNumber, category.getId()));
        reviewsByOrderType.put(OrderType.DATE_ASC, reviewService.getAllNotHiddenReviewsByCategoryDateAsc(pageNumber, category.getId()));
        reviewsByOrderType.put(OrderType.SCORE_DESC, reviewService.getAllNotHiddenReviewsByTotalScoreDesc(pageNumber, category));
        reviewsByOrderType.put(OrderType.SCORE_ASC, reviewService.getAllNotHiddenReviewsByTotalScoreAsc(pageNumber, category));
    }

    public void mapReviewsByUserToOrderType(int pageNumber, User user) {
        reviewsByOrderTypeAndUser.put(OrderType.DEFAULT, reviewService.getAllReviewsByReviewer(pageNumber, user.getUsername()));
        reviewsByOrderTypeAndUser.put(OrderType.COMMENT_COUNT_DESC, reviewService.getAllNotHiddenByUserAndCommentCountDesc(pageNumber, user));
        reviewsByOrderTypeAndUser.put(OrderType.COMMENT_COUNT_ASC, reviewService.getAllNotHiddenByUserAndCommentCountAsc(pageNumber, user));
        reviewsByOrderTypeAndUser.put(OrderType.DATE_DESC, reviewService.getAllNotHiddenByUserAndDateDesc(pageNumber, user));
        reviewsByOrderTypeAndUser.put(OrderType.DATE_ASC, reviewService.getAllNotHiddenByUserAndDateAsc(pageNumber, user));
        reviewsByOrderTypeAndUser.put(OrderType.SCORE_DESC, reviewService.getAllNotHiddenByUserAndScoreDesc(pageNumber, user));
        reviewsByOrderTypeAndUser.put(OrderType.SCORE_ASC, reviewService.getAllNotHiddenByUserAndScoreAsc(pageNumber, user));
    }

    public void mapCommentsByReviewToOrderType(int pageNumber, Review review) {
        commentsByOrderType.put(OrderType.DEFAULT, commentService.getAllCommentsByReview(pageNumber, review));
        commentsByOrderType.put(OrderType.DATE_DESC, commentService.getAllCommentsByDateDesc(pageNumber, review.getId()));
        commentsByOrderType.put(OrderType.DATE_ASC, commentService.getAllCommentsByDateAsc(pageNumber, review.getId()));
        commentsByOrderType.put(OrderType.SCORE_DESC, commentService.getAllCommentsByScoreDesc(pageNumber, review.getId()));
        commentsByOrderType.put(OrderType.SCORE_ASC, commentService.getAllCommentsByScoreAsc(pageNumber, review.getId()));
    }

    public void mapCommentsByUserToOrderType(int pageNumber, User user) {
        commentsByOrderTypeAndUser.put(OrderType.DEFAULT, commentService.getAllCommentsByUser(pageNumber, user));
        commentsByOrderTypeAndUser.put(OrderType.DATE_DESC, commentService.getAllCommentsByUserDateDesc(pageNumber, user));
        commentsByOrderTypeAndUser.put(OrderType.DATE_ASC, commentService.getAllCommentsByUserDateAsc(pageNumber, user));
        commentsByOrderTypeAndUser.put(OrderType.SCORE_DESC, commentService.getAllCommentsByUserScoreDesc(pageNumber, user));
        commentsByOrderTypeAndUser.put(OrderType.SCORE_ASC, commentService.getAllCommentsByUserScoreAsc(pageNumber, user));
    }

    public void mapCategoriesToOrderType(int pageNumber) {
        categoriesByOrderType.put(OrderType.DEFAULT, categoryService.getAllCategories(pageNumber));
        categoriesByOrderType.put(OrderType.POST_COUNT_DESC, categoryService.getAllCategoriesByPostCountDesc(pageNumber));
        categoriesByOrderType.put(OrderType.POST_COUNT_ASC, categoryService.getAllCategoriesByPostCountAsc(pageNumber));
        categoriesByOrderType.put(OrderType.FOLLOWER_COUNT_DESC, categoryService.getAllCategoriesByFollowersCountDesc(pageNumber));
        categoriesByOrderType.put(OrderType.FOLLOWER_COUNT_ASC, categoryService.getAllCategoriesByFollowersCountAsc(pageNumber));
        categoriesByOrderType.put(OrderType.POST_DATE_DESC, categoryService.getAllCategoriesByNewestPost(pageNumber));
        categoriesByOrderType.put(OrderType.POST_DATE_ASC, categoryService.getAllCategoriesByOldestPost(pageNumber));
    }


}
