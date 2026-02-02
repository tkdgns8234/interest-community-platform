package com.findy.boundedcontext.post.app.usecase;

import com.findy.global.event.EventPublisher;
import com.findy.boundedcontext.post.app.dto.CreatePostCommand;
import com.findy.boundedcontext.post.app.interfaces.PostRepository;
import com.findy.shared.post.event.PostCreatedEvent;
import com.findy.boundedcontext.post.domain.model.post.Post;
import com.findy.boundedcontext.post.domain.model.post.PostInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreatePostUseCase {
    private final PostRepository postRepository;
    private final EventPublisher eventPublisher;

    @Transactional
    public Post execute(CreatePostCommand command) {
        PostInfo postInfo = new PostInfo(command.title(), command.content());
        Post post = new Post(null, command.authorId(), postInfo);
        post = postRepository.save(post);

        // 이벤트 발행
        PostCreatedEvent event = PostCreatedEvent.builder()
            .postId(post.getId())
            .authorId(post.getAuthorId())
            .title(post.getPostInfo().getTitle())
            .build();
        eventPublisher.publish(event);

        return post;
    }
}
