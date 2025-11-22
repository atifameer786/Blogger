package com.blog.blogger.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.blogger.pojo.APIResponse;
import com.blog.blogger.pojo.CategoryDto;
import com.blog.blogger.service.CategoryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {

        CategoryDto createCategoryDto = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(createCategoryDto, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
            @PathVariable("categoryId") Integer cId) {

        CategoryDto updateCategoryDto = this.categoryService.updateCategory(categoryDto, cId);
        return ResponseEntity.ok(updateCategoryDto);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<APIResponse> deleteCategory(@PathVariable("categoryId") Integer cId){
        categoryService.deleteCategory(cId);

        return new ResponseEntity<APIResponse>(new APIResponse("200","Category Deleted SuccessFully"),HttpStatus.OK);
        
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") Integer cId) {
        
        return  ResponseEntity.ok(categoryService.getCategory(cId));
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        
        return ResponseEntity.ok(this.categoryService.getCategories());
    }
    
    


}
