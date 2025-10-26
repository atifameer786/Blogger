package com.blog.blogger.service;

import java.util.List;

import com.blog.blogger.entity.Post;
import com.blog.blogger.pojo.PostDto;

public interface PostService {

    Post createPost(PostDto postDto);
    Post udpatePost(PostDto postDto,Integer id);
    Post getPostById(Integer id);
    void deletePost(Integer id);
    List<Post> getAllPosts();
    List<Post> getPostsByCategory(Integer categoryId);
    List<Post> getPostsByUser(Integer userId);
    List<Post> searchPosts(String keyword);
}
