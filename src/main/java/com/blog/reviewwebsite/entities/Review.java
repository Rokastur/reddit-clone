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
public class Review extends Content{

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "reviewScore",
            joinColumns = @JoinColumn(name = "review_id"),
            inverseJoinColumns = @JoinColumn(name = "score_id")
    )
    private Set<Score> reviewScore = new HashSet<>();

    public void addScore(Score score) {
        reviewScore.add(score);
        score.getReviewScore().add(this);
    }

    public void removeScore(Score score) {
        reviewScore.remove(score);
        score.getReviewScore().remove(this);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "review")
    private Set<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

}
