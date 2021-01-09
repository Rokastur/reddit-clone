package com.blog.reviewwebsite.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "date")
    @CreationTimestamp
    private LocalDateTime date;

    @Column(name = "profile_description", columnDefinition = "text")
    private String profileDescription;

    @Column(name = "incognito")
    private boolean incognito;

    @OneToMany(mappedBy = "user")
    private Set<File> files;

    public void addFile(File file) {
        this.files.add(file);
        file.setUser(this);
    }

    @OneToMany(mappedBy = "user")
    private Set<Category> categories = new HashSet<>();

    public void addCategory(Category category) {
        this.categories.add(category);
        category.setUser(this);
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Review> reviews = new HashSet<>();

    public void addReview(Review review) {
        this.reviews.add(review);
        review.setUser(this);
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setUser(this);
    }

    @OneToMany(mappedBy = "user")
    private Set<Score> score = new HashSet<>();

    public void addScore(Score score) {
        this.score.add(score);
        score.setUser(this);
    }

    @OneToMany(mappedBy = "user")
    private Set<Message> messages = new HashSet<>();

    public void addMessage(Message message) {
        this.messages.add(message);
        message.setUser(this);
    }

    @ManyToMany
    @JoinTable(name = "users_chat",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id"))
    private Set<Chat> chatSet = new HashSet<>();

    public void addChatSet(Chat chat) {
        chatSet.add(chat);
        chat.getChatters().add(this);
    }

    public void removeChatSet(Chat chat) {
        chatSet.remove(chat);
        chat.getChatters().remove(this);
    }

    @ManyToMany
    @JoinTable(name = "saved_reviews",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "review_id"))
    private Set<Review> savedReviews = new HashSet<>();

    public void addToSavedReviews(Review review) {
        savedReviews.add(review);
        review.getUsersWhoSavedReview().add(this);
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "followedCategories",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> followedCategories = new HashSet<>();

    public void addFollowedCategory(Category category) {
        followedCategories.add(category);
        category.getFollowers().add(this);
    }

    public void removeFollowedCategory(Category category) {
        followedCategories.remove(category);
        category.getFollowers().remove(this);
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

