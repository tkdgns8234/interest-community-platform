package com.findy.boundedcontext.post.app.usecase;

import com.findy.boundedcontext.post.app.interfaces.CommentRepository;
import com.findy.boundedcontext.post.domain.model.comment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCommentsByPostIdUseCase {
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<Comment> execute(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}
