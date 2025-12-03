package com.blog.blogger.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.blogger.config.AppConstants;
import com.blog.blogger.pojo.PostDto;
import com.blog.blogger.pojo.PostResponse;
import com.blog.blogger.service.FileService;
import com.blog.blogger.service.PostService;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
            @PathVariable Integer categoryId) {

        PostDto createPost = postService.createPost(postDto, userId, categoryId);

        return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);

    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<PostResponse> getPostsByUser(
            @PathVariable Integer userId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR) String sortDir) {

        PostResponse postResponse = this.postService.getPostsByUser(userId, pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<PostResponse> getPostsByCategory(
            @PathVariable Integer categoryId,
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR) String sortDir) {

        PostResponse postResponse = this.postService.getPostsByCategory(categoryId, pageNumber, pageSize, sortBy,
                sortDir);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR) String sortDir) {

        PostResponse postResponse = this.postService.getPosts(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer id) {
        PostDto postDto = this.postService.getPostById(id);
        return ResponseEntity.ok(postDto);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer id) {
        PostDto updatedPost = this.postService.udpatePost(postDto, id);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Integer id) {
        this.postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyword) {
        List<PostDto> result = this.postService.searchPosts(keyword);
        return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);

    }

    @PostMapping("posts/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
            @PathVariable Integer postId) throws IOException {

        String fileName = this.fileService.uploadImage(path, image);
        PostDto postDto = this.postService.getPostById(postId);
        postDto.setImageName(fileName);
        PostDto updatedPost = this.postService.udpatePost(postDto, postId);
        return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);

    }

    @GetMapping(value = "/posts/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)
            throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }
}
