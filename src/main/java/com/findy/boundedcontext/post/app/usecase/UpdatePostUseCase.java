package com.findy.boundedcontext.post.app.usecase;

import com.findy.boundedcontext.post.app.dto.UpdatePostCommand;
import com.findy.boundedcontext.post.app.interfaces.PostRepository;
import com.findy.boundedcontext.post.domain.model.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdatePostUseCase {
    private final PostRepository postRepository;

    @Transactional
    public Post execute(UpdatePostCommand command) {
        Post post = postRepository.findById(command.id());
        post.updateTitle(command.title());
        post.updateContent(command.content());
        return postRepository.save(post);
    }
}
