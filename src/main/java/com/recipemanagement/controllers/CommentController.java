package com.recipemanagement.controllers;

import com.recipemanagement.models.Comment;
import com.recipemanagement.models.User;
import com.recipemanagement.models.data.CommentRepository;
import com.recipemanagement.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{commentId}")
    public Comment getComment(@PathVariable int commentId) {
        Comment comment = commentRepository.findCommentById(commentId);
        if (comment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }
        return comment;
    }

    @PutMapping("/update/{commentId}")
    public Comment updateComment(@PathVariable int commentId, @RequestBody Comment updatedComment,
                                 @RequestParam String email) {
        User currentUser = userRepository.findUserByEmail(email);
        if(currentUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid User");
        }

        Comment existing = commentRepository.findCommentById(commentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found00"));

        existing.setComment(updatedComment.getComment());
        return commentRepository.save(existing);
    }

    public String deleteComment(@PathVariable int commentId, @RequestParam String email) {
        return userRepository.deleteComment(commentId, email);
    }
}
