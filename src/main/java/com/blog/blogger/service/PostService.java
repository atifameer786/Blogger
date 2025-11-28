package com.blog.blogger.service;

import java.util.List;

import com.blog.blogger.entity.Post;
import com.blog.blogger.pojo.PostDto;
import com.blog.blogger.pojo.PostResponse;

public interface PostService {

    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

    PostDto udpatePost(PostDto postDto, Integer id);

    PostDto getPostById(Integer id);

    void deletePost(Integer id);

    PostResponse getPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy,
            String sortDir);

    PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    List<PostDto> searchPosts(String keyword);
}
