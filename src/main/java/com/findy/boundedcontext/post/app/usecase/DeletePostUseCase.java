package com.findy.boundedcontext.post.app.usecase;

import com.findy.boundedcontext.post.app.interfaces.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeletePostUseCase {
    private final PostRepository postRepository;

    @Transactional
    public void execute(Long id) {
        postRepository.deleteById(id);
    }
}
