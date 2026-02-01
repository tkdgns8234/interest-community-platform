package com.findy.boundedcontext.post.app;

import com.findy.global.event.EventPublisher;
import com.findy.boundedcontext.post.app.dto.CreatePostCommand;
import com.findy.boundedcontext.post.app.dto.UpdatePostCommand;
import com.findy.boundedcontext.post.app.interfaces.PostRepository;
import com.findy.shared.post.event.PostCreatedEvent;
import com.findy.boundedcontext.post.domain.model.post.Post;
import com.findy.boundedcontext.post.domain.model.post.PostInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final EventPublisher eventPublisher;

    @Transactional
    public Post createPost(CreatePostCommand command) {
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

    @Transactional(readOnly = true)
    public Post getPost(Long id) {
        return postRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Post> getAllPosts(Long cursor, int size) {
        return postRepository.findAll(cursor, size);
    }

    @Transactional
    public Post updatePost(UpdatePostCommand command) {
        Post post = postRepository.findById(command.id());
        post.setTitle(command.title());
        post.setContent(command.content());
        return postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
