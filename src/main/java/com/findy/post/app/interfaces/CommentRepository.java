package com.findy.post.app.interfaces;

import com.findy.post.domain.model.comment.Comment;

import java.util.List;

public interface CommentRepository {
    Comment save(Comment comment);
    Comment findById(Long id);
    void deleteById(Long id);
    List<Comment> findByPostId(Long postId);
}
