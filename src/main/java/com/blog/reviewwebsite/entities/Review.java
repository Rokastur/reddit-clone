package com.blog.reviewwebsite.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Review")
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @Column(name = "author")
    private String author;

    @Column(name = "title")
    private String title;

    @Column(name = "rating")
    private int rating;

    @Column(name = "review", columnDefinition = "text")
    private String review;
}
