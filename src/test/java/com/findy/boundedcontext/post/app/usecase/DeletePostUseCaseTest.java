package com.findy.boundedcontext.post.app.usecase;

import com.findy.boundedcontext.post.app.interfaces.PostRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeletePostUseCaseTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private DeletePostUseCase deletePostUseCase;

    @Test
    @DisplayName("게시글을 삭제할 수 있다")
    void deletePost() {
        deletePostUseCase.execute(1L);

        verify(postRepository).deleteById(1L);
    }
}
