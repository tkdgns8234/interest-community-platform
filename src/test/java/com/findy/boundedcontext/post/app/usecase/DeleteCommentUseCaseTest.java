package com.findy.boundedcontext.post.app.usecase;

import com.findy.boundedcontext.post.app.interfaces.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteCommentUseCaseTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private DeleteCommentUseCase deleteCommentUseCase;

    @Test
    @DisplayName("댓글을 삭제할 수 있다")
    void deleteComment() {
        deleteCommentUseCase.execute(1L);

        verify(commentRepository).deleteById(1L);
    }
}
