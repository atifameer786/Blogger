package com.blog.blogger.service;

import java.util.List;

import com.blog.blogger.pojo.CategoryDto;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto category);
    CategoryDto updateCategory(CategoryDto category,Integer id);
    void deleteCategory(Integer id);
    CategoryDto getCategory(Integer id);
    List<CategoryDto> getCategories();


    
}
