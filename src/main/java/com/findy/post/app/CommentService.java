package com.findy.post.app;

import com.findy.post.app.dto.CreateCommentCommand;
import com.findy.post.app.dto.UpdateCommentCommand;
import com.findy.post.app.interfaces.CommentRepository;
import com.findy.post.domain.model.comment.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public Comment createComment(CreateCommentCommand command) {
        Comment comment = new Comment(null, command.postId(), command.authorId(), command.content());
        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public Comment getComment(Long id) {
        return commentRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    @Transactional
    public Comment updateComment(UpdateCommentCommand command) {
        Comment comment = commentRepository.findById(command.id());
        comment.updateContent(command.content());
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
