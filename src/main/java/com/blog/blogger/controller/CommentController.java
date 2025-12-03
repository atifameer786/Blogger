package com.blog.blogger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.blog.blogger.pojo.CommentDto;
import com.blog.blogger.service.CommentService;
import com.blog.blogger.pojo.APIResponse;

@RestController
@RequestMapping("/api/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Create comment for a post
    @PostMapping("/post/{postId}/user/{userId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @PathVariable Integer postId,
            @PathVariable Integer userId) {

        CommentDto createdComment = commentService.createComment(commentDto, postId,userId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    // Delete comment by ID
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<APIResponse> deleteComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new APIResponse("200", "Comment deleted successfully"), HttpStatus.OK);
    }
}
