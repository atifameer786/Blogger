package com.blog.blogger.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.blogger.entity.Category;
import com.blog.blogger.exception.ResourceNotFoundException;
import com.blog.blogger.pojo.CategoryDto;
import com.blog.blogger.repository.CategoryRepository;
import com.blog.blogger.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category saveCategory = this.categoryRepository.save(category);
        return modelMapper.map(saveCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer id) {

        Category category = this.categoryRepository.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException("Category","id",id));
        category.setDescription(categoryDto.getDescription());
        category.setTitle(categoryDto.getTitle());

        Category updatedCategory = this.categoryRepository.save((category));
        return modelMapper.map(updatedCategory, CategoryDto.class);

        
    }

    @Override
    public void deleteCategory(Integer id) {
       Category category = this.categoryRepository.findById(id)
       .orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));
       this.categoryRepository.delete(category);
    }

    @Override
    public CategoryDto getCategory(Integer id) {
        Category category = this.categoryRepository.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException("Category", "id", id));

        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
       List<Category> categories = this.categoryRepository.findAll();
       List<CategoryDto> dtoCategories = new ArrayList<>();

       for(Category category:categories){
            CategoryDto dto= modelMapper.map(category, CategoryDto.class);
            dtoCategories.add(dto);
            
       }
       return dtoCategories;
    }
    
    
}
