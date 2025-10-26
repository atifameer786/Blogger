package com.blog.blogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blogger.entity.User;

public interface UserRepository extends JpaRepository<User,Integer>{
    
}
