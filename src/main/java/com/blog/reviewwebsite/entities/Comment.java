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
@Table(name = "comments")
public class Comment extends Content {

    @Column(name = "text")
    @NotBlank
    private String text;

    @Column(name = "date")
    @CreationTimestamp
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @ManyToMany
    @JoinTable(
            name = "commentScore",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "score_id")
    )
    private Set<Score> commentScore = new HashSet<>();

    public void addScore(Score score) {
        commentScore.add(score);
        score.getCommentScore().add(this);
    }

    public void removeScore(Score score) {
        commentScore.remove(score);
        score.getCommentScore().remove(this);
    }

}
