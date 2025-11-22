package com.blog.blogger.service;

import java.util.List;

import com.blog.blogger.entity.Post;
import com.blog.blogger.pojo.PostDto;

public interface PostService {

    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
    Post udpatePost(PostDto postDto,Integer id);
    PostDto getPostById(Integer id);
    void deletePost(Integer id);
    List<PostDto> getPosts();
    List<PostDto> getPostsByCategory(Integer categoryId);
    List<PostDto> getPostsByUser(Integer userId);
    List<Post> searchPosts(String keyword);
}
