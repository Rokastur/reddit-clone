package com.blog.reviewwebsite.entities;

import com.blog.reviewwebsite.controller.RatingType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
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
    private RatingType ratingType = RatingType.NONE;

    @ManyToMany(mappedBy = "reviewScore")
    private Set<Review> reviewScore = new HashSet<>();

    @ManyToMany(mappedBy = "commentScore")
    private Set<Comment> commentScore = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;
}
