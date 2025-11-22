package com.blog.blogger.service.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.blog.blogger.entity.Category;
import com.blog.blogger.entity.Post;
import com.blog.blogger.entity.User;
import com.blog.blogger.exception.ResourceNotFoundException;
import com.blog.blogger.pojo.PostDto;
import com.blog.blogger.repository.CategoryRepository;
import com.blog.blogger.repository.PostRepository;
import com.blog.blogger.repository.UserRepository;
import com.blog.blogger.service.PostService;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "User Id", userId));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category", "category id", categoryId));

        Post post = modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setCategory(category);
        post.setUser(user);
        Post newPost = postRepository.save(post);
        return modelMapper.map(newPost, PostDto.class);
    }

    @Override
    public Post udpatePost(PostDto postDto, Integer id) {

    Post post = this.postRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", id));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post updatedPost = this.postRepository.save(post);
    return updatedPost;
}

    @Override
    public PostDto getPostById(Integer id) {
        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", id));

        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public void deletePost(Integer id) {
        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", id));

        this.postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPosts() {

        List<Post> posts = postRepository.findAll();
        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtos;

    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", categoryId));
        List<Post> posts = this.postRepository.findByCategory(category);

        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user Id", userId));

        List<Post> posts = this.postRepository.findByUser(user);

        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<Post> searchPosts(String keyword) {
        return this.postRepository.findByTitleContaining(keyword);
    }

}
