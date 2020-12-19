package com.blog.reviewwebsite.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    private String message;

    @ManyToMany(mappedBy = "chatSet")
    private Set<User> chatters = new HashSet<>();
}
