package com.blog.reviewwebsite.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String username;

    private String password;

    private String confirmPassword;

}