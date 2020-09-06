package com.blog.reviewwebsite.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Review")
public class Review {

    public Review() {
    }

    public Review(String username) {
        this.reviewer = username;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(name = "reviewer")
    private String reviewer;

    @Column(name = "author")
    private String author;

    @Column(name = "title")
    private String title;

    @Column(name = "rating")
    private int rating;

    @Column(name = "review", columnDefinition = "text")
    private String reviewText;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private User user;

}
