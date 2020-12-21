package com.blog.reviewwebsite.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @Column(name = "date")
    @CreationTimestamp
    private LocalDateTime date;

    @OneToMany(mappedBy = "chat")
    private Set<Message> messages;

    @ManyToMany(mappedBy = "chatSet", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<User> chatters = new HashSet<>();
}
