package com.findy.boundedcontext.post.app.interfaces;

import com.findy.boundedcontext.post.domain.model.comment.Comment;

import java.util.List;

public interface CommentRepository {
    Comment save(Comment comment);
    Comment findById(Long id);
    void deleteById(Long id);
    List<Comment> findByPostId(Long postId);
}
