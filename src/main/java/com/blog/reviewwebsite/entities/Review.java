package com.blog.reviewwebsite.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

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

    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "review")
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", hidden=" + hidden +
                ", date=" + date +
                '}';
    }
}
