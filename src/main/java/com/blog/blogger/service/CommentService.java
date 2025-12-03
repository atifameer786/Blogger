package com.blog.blogger.service;

import com.blog.blogger.pojo.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto,Integer postId,Integer userId);
    void deleteComment(Integer commentId);
}
