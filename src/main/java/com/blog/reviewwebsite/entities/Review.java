package com.blog.reviewwebsite.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
    @NotBlank
    private String author;

    @Column(name = "title")
    @NotBlank
    private String title;

    @Column(name = "rating")
    private int rating;

    @Column(name = "review_title")
    @NotBlank
    private String reviewTitle;

    @Column(name = "review", columnDefinition = "text")
    @NotBlank
    private String reviewText;

    @Column(name = "hidden")
    private Boolean hidden;

    @Column(name = "date")
    @CreationTimestamp
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "review")
    private List<Comment> comments;

    @OneToMany(mappedBy = "review")
    private Set<Score> scoreSet;

    @Column(name = "total_upvotes")
    private int totalUpvotes = 0;

    @Column(name = "total_downvotes")
    private int totalDownvotes = 0;

    @Column(name = "total_score")
    private int totalScore = 0;

}
