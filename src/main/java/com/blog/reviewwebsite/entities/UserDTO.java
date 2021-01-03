package com.blog.reviewwebsite.entities;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserDTO {

    @NotEmpty
    @Min(3)
    private String username;

    @NotEmpty
    @Min(3)
    private String password;

    private String confirmPassword;

}