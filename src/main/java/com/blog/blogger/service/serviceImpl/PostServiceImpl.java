package com.blog.blogger.service.serviceImpl;

import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.blogger.entity.Category;
import com.blog.blogger.entity.Post;
import com.blog.blogger.entity.User;
import com.blog.blogger.exception.ResourceNotFoundException;
import com.blog.blogger.pojo.PostDto;
import com.blog.blogger.pojo.PostResponse;
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
    public PostDto udpatePost(PostDto postDto, Integer id) {

        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", id));

        if (postDto.getTitle() != null)
            post.setTitle(postDto.getTitle());
        if (postDto.getContent() != null)
            post.setContent(postDto.getContent());
        if (postDto.getImageName() != null)
            post.setImageName(postDto.getImageName());

        Post updatedPost = this.postRepository.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);
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
    public PostResponse getPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = this.postRepository.findAll(p);

        return createPostResponse(pagePost);
    }

    @Override
    public PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy,
            String sortDir) {

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", categoryId));

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = this.postRepository.findByCategory(category, p);

        return createPostResponse(pagePost);
    }

    @Override
    public PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy,
            String sortDir) {

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user Id", userId));

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = this.postRepository.findByUser(user, p);

        return createPostResponse(pagePost);
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts =postRepository.findByTitleContaining(keyword);
        List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    private PostResponse createPostResponse(Page<Post> pagePost) {
        List<PostDto> postDtos = pagePost.getContent()
                .stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

}
