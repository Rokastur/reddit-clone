package com.blog.reviewwebsite.entities;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserDTO {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
    
    private String confirmPassword;

}