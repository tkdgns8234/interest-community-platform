package com.findy.boundedcontext.post.app.usecase;

import com.findy.global.event.EventPublisher;
import com.findy.boundedcontext.post.app.dto.CreateCommentCommand;
import com.findy.boundedcontext.post.app.interfaces.CommentRepository;
import com.findy.boundedcontext.post.app.interfaces.PostRepository;
import com.findy.shared.post.event.CommentCreatedEvent;
import com.findy.boundedcontext.post.domain.model.comment.Comment;
import com.findy.boundedcontext.post.domain.model.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateCommentUseCase {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final EventPublisher eventPublisher;

    @Transactional
    public Comment execute(CreateCommentCommand command) {
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
}
