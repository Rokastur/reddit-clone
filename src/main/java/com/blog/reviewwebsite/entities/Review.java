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
        this.username = username;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "title")
    @NotBlank
    private String title;

    @Column(name = "text", columnDefinition = "text")
    @NotBlank
    private String text;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "review")
    private Set<Score> scoreSet;

    @Column(name = "total_upvotes")
    private int totalUpvotes = 0;

    @Column(name = "total_downvotes")
    private int totalDownvotes = 0;

    @Column(name = "total_score")
    private int totalScore = 0;

    @Column(name = "comment_count")
    private int commentCount = 0;

}
