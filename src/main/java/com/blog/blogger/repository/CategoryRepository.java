package com.blog.blogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.blog.blogger.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer>{
    
}
