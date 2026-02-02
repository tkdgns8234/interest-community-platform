package com.findy.boundedcontext.post.app.usecase;

import com.findy.boundedcontext.post.app.interfaces.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteCommentUseCase {
    private final CommentRepository commentRepository;

    @Transactional
    public void execute(Long id) {
        commentRepository.deleteById(id);
    }
}
