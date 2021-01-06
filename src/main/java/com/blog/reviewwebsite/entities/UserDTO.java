package com.blog.reviewwebsite.entities;

import com.blog.reviewwebsite.validator.MatchingPasswords;
import com.blog.reviewwebsite.validator.UniqueUsername;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@MatchingPasswords(password = "password",
        confirmPassword = "confirmPassword")
public class UserDTO {

    @NotEmpty(message = "username must not be empty")
    @Size(min = 3, message = "minimum length must be at least 3 characters")
    @UniqueUsername
    private String username;

    @NotEmpty(message = "password must not be empty")
    @Size(min = 3, message = "minimum length must be at least 3 characters")
    private String password;

    private String confirmPassword;

}