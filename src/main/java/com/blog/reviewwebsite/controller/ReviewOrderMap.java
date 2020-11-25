package com.blog.reviewwebsite.controller;

import com.blog.reviewwebsite.entities.Category;
import com.blog.reviewwebsite.entities.Review;
import com.blog.reviewwebsite.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ReviewOrderMap {

    @Autowired
    private ReviewService reviewService;

    public Map<OrderType, Page<Review>> reviewsByOrderType = new HashMap<>();

    public void assignReviewsToReviewsByOrderTypeMap(int pageNumber, Category category) {
        reviewsByOrderType.put(OrderType.DEFAULT, reviewService.getAllNotHiddenReviewsByCategory(pageNumber, category));
        reviewsByOrderType.put(OrderType.COMMENT_COUNT_DESC, reviewService.getAllNotHiddenByCommentCountDesc(pageNumber, category));
        reviewsByOrderType.put(OrderType.COMMENT_COUNT_ASC, reviewService.getAllNotHiddenByCommentCountAsc(pageNumber, category));
        reviewsByOrderType.put(OrderType.DATE_DESC, reviewService.getAllNotHiddenReviewsByCategoryDateDesc(pageNumber, category.getId()));
        reviewsByOrderType.put(OrderType.DATE_ASC, reviewService.getAllNotHiddenReviewsByCategoryDateAsc(pageNumber, category.getId()));
        reviewsByOrderType.put(OrderType.SCORE_DESC, reviewService.getAllNotHiddenReviewsByTotalScoreDesc(pageNumber, category));
        reviewsByOrderType.put(OrderType.SCORE_ASC, reviewService.getAllNotHiddenReviewsByTotalScoreAsc(pageNumber, category));
    }


}
