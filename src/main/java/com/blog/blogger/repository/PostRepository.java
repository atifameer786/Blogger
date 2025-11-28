package com.blog.blogger.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blogger.entity.Category;
import com.blog.blogger.entity.Post;
import com.blog.blogger.entity.User;

public interface PostRepository extends JpaRepository<Post,Integer>{

    
    Page<Post> findByCategory(Category category, Pageable pageable);
    Page<Post> findByUser(User user, Pageable pageable);
    List<Post> findByTitleContaining(String keyword);
    
}
