package com.blog.blogger.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blogger.entity.User;

public interface UserRepository extends JpaRepository<User,Integer>{

    @EntityGraph("User.posts.comments.roles")
    Optional<User> findByEmail(String email);
    
}
