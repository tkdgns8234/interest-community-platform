package com.findy.boundedcontext.post.app.usecase;

import com.findy.boundedcontext.post.app.interfaces.PostRepository;
import com.findy.boundedcontext.post.domain.model.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllPostsUseCase {
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<Post> execute(Long cursor, int size) {
        return postRepository.findAll(cursor, size);
    }
}
