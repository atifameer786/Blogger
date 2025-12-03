package com.blog.blogger.pojo;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private int id;
    @NotEmpty
    @Size(min=4,message = "User name must be at least 4 characters !")
    private String name;
    @Email(message = "Email Address is not valid")
    private String email;
    @NotEmpty
    @Size(min = 3,max=10,message = "Password must be min of 3 char and max of 10 char")
    private String password;
    @NotEmpty
    private String about;

    @JsonIgnore
    private Set<PostDto> posts = new HashSet<>();
    @JsonIgnore
    private Set<CommentDto> comments = new HashSet<>();
    
}
