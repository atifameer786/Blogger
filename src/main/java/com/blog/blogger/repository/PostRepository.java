package com.blog.blogger.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blogger.entity.Category;
import com.blog.blogger.entity.Post;
import com.blog.blogger.entity.User;

public interface PostRepository extends JpaRepository<Post,Integer>{

    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
    
}
