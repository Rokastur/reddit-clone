package com.blog.reviewwebsite.entities;

import com.blog.reviewwebsite.controller.Roles;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {

    public Role() {
    }

    public Role(Roles role) {
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Roles role;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
