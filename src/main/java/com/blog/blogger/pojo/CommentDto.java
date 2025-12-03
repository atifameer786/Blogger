package com.blog.blogger.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {

    private Integer id;
    private String content;
    @JsonIgnore 
    private PostDto post;
    private UserDto user;
    
}
