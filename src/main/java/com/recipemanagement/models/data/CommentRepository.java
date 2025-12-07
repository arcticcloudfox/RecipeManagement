package com.recipemanagement.models.data;

import com.recipemanagement.models.Comment;
import com.recipemanagement.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAll();
    Comment findCommentById(int commentId);
    void deleteById(int commentId);

}
