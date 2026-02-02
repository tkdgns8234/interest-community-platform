package com.findy.boundedcontext.post.app.usecase;

import com.findy.boundedcontext.post.app.dto.UpdateCommentCommand;
import com.findy.boundedcontext.post.app.interfaces.CommentRepository;
import com.findy.boundedcontext.post.domain.model.comment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateCommentUseCase {
    private final CommentRepository commentRepository;

    @Transactional
    public Comment execute(UpdateCommentCommand command) {
        Comment comment = commentRepository.findById(command.id());
        comment.updateContent(command.content());
        return commentRepository.save(comment);
    }
}
