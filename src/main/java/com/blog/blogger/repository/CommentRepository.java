package com.blog.blogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blogger.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment,Integer>{
    
}
