package com.blog.reviewwebsite.entities;

import com.blog.reviewwebsite.controller.RatingType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "Score")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rating_type")
    @Enumerated(EnumType.STRING)
    private RatingType ratingType;

    @ManyToMany(mappedBy = "reviewScore")
    private Set<Review> reviewScore;

    @ManyToMany(mappedBy = "commentScore")
    private Set<Comment> commentScore;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
