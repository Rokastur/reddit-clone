package com.blog.reviewwebsite.entities;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDTO {

    @NotEmpty(message = "username must not be empty")
    @Size(min = 3, message = "minimum length must be at least 3 characters")
    private String username;

    @NotEmpty(message = "password must not be empty")
    @Size(min = 3, message = "minimum length must be at least 3 characters")
    private String password;

    private String confirmPassword;

}