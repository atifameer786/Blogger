package com.blog.blogger.pojo;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class PostDto {


    private String title;
    private String content;
    private Date addedDate;
    private String imageName;
    private UserDto user;
    private CategoryDto category;
    
}
