package com.blog.blogger.service.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.blogger.entity.Comment;
import com.blog.blogger.entity.Post;
import com.blog.blogger.entity.User;
import com.blog.blogger.exception.ResourceNotFoundException;
import com.blog.blogger.pojo.CommentDto;
import com.blog.blogger.repository.CommentRepository;
import com.blog.blogger.repository.PostRepository;
import com.blog.blogger.repository.UserRepository;
import com.blog.blogger.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Comment comment = modelMapper.map(commentDto, Comment.class);

        comment.setPost(post);
        comment.setUser(user);

        Comment savedComment = commentRepository.save(comment);

        return modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        commentRepository.delete(comment);
    }

}
