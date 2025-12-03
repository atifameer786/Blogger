package com.blog.blogger.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.blog.blogger.entity.Comment;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class PostDto {


    private Integer id;
    private String title;
    private String content;
    private Date addedDate;
    private String imageName;
    private UserDto user;
    private CategoryDto category;
    private Set<CommentDto> comments = new HashSet<>();
    
}
