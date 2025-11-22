package com.blog.blogger.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.blogger.entity.Post;
import com.blog.blogger.pojo.PostDto;
import com.blog.blogger.repository.PostRepository;
import com.blog.blogger.service.PostService;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/")
public class PostController {

    private final ModelMapper modelMapper;

    @Autowired
    private PostService postService;

    PostController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
            @PathVariable Integer categoryId) {

        PostDto createPost = postService.createPost(postDto, userId, categoryId);

        return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);

    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId) {

        List<PostDto> posts = this.postService.getPostsByUser(userId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

    }

    @GetMapping("/category/{categoryId}/posts")

    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId) {

        List<PostDto> posts = this.postService.getPostsByCategory(categoryId);

        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getPosts() {
        return ResponseEntity.ok(this.postService.getPosts());
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer id) {
        PostDto postDto = this.postService.getPostById(id);
        return ResponseEntity.ok(postDto);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer id) {
        Post updatedPost = this.postService.udpatePost(postDto, id);
        return ResponseEntity.ok(this.modelMapper.map(updatedPost, PostDto.class));
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Integer id) {
        this.postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword) {
        List<Post> posts = this.postService.searchPosts(keyword);
        List<PostDto> postDtos = posts.stream()
                .map(post -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(postDtos);
    }

}
