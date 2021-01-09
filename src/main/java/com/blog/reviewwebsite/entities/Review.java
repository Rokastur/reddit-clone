package com.blog.reviewwebsite.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Review")
public class Review extends Content {

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
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "review")
    private Set<Comment> comments = new HashSet<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setReview(this);
    }

    @OneToMany(mappedBy = "review")
    private Set<Score> score = new HashSet<>();

    public void addScore(Score score) {
        this.score.add(score);
        score.setReview(this);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToMany(mappedBy = "bookmarkedReviews")
    private Set<User> usersWhoBookmarkedReview = new HashSet<>();

}
