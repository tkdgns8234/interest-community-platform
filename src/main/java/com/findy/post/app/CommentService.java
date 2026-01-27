package com.findy.post.app;

import com.findy.common.event.EventPublisher;
import com.findy.post.app.dto.CreateCommentCommand;
import com.findy.post.app.dto.UpdateCommentCommand;
import com.findy.post.app.interfaces.CommentRepository;
import com.findy.post.app.interfaces.PostRepository;
import com.findy.post.domain.event.CommentCreatedEvent;
import com.findy.post.domain.model.comment.Comment;
import com.findy.post.domain.model.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final EventPublisher eventPublisher;

    @Transactional
    public Comment createComment(CreateCommentCommand command) {
        Comment comment = new Comment(null, command.postId(), command.authorId(), command.content());
        comment = commentRepository.save(comment);

        // 게시글 조회하여 작성자 ID 가져오기
        Post post = postRepository.findById(command.postId());

        // 이벤트 발행
        CommentCreatedEvent event = CommentCreatedEvent.builder()
            .commentId(comment.getId())
            .postId(comment.getPostId())
            .commentAuthorId(comment.getAuthorId())
            .postAuthorId(post.getAuthorId())
            .commentContent(comment.getContent())
            .build();
        eventPublisher.publish(event);

        return comment;
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
