package com.blog.reviewwebsite.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
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

    @Column(name = "review_title")
    private String reviewTitle;

    @Column(name = "review", columnDefinition = "text")
    private String reviewText;

    @Column(name = "hidden")
    private Boolean hidden;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "review")
    private List<Comment> comments;

}
