package com.findy.boundedcontext.post.app.usecase;

import com.findy.boundedcontext.post.app.interfaces.PostRepository;
import com.findy.boundedcontext.post.domain.model.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetPostUseCase {
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public Post execute(Long id) {
        return postRepository.findById(id);
    }
}
