package com.findy.boundedcontext.post.app.usecase;

import com.findy.boundedcontext.post.app.interfaces.CommentRepository;
import com.findy.boundedcontext.post.domain.model.comment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetCommentUseCase {
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public Comment execute(Long id) {
        return commentRepository.findById(id);
    }
}
