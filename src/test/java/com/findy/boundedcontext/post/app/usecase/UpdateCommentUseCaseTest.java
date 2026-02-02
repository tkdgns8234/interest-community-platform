package com.findy.boundedcontext.post.app.usecase;

import com.findy.boundedcontext.post.app.dto.UpdateCommentCommand;
import com.findy.boundedcontext.post.app.interfaces.CommentRepository;
import com.findy.boundedcontext.post.domain.model.comment.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdateCommentUseCaseTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private UpdateCommentUseCase updateCommentUseCase;

    private Comment testComment;

    @BeforeEach
    void setUp() {
        testComment = new Comment(1L, 1L, 1L, "테스트 댓글");
    }

    @Test
    @DisplayName("댓글을 수정할 수 있다")
    void updateComment() {
        given(commentRepository.findById(1L)).willReturn(testComment);
        given(commentRepository.save(any(Comment.class))).willReturn(testComment);

        UpdateCommentCommand command = new UpdateCommentCommand(1L, "수정된 댓글");
        Comment updatedComment = updateCommentUseCase.execute(command);

        assertThat(updatedComment).isNotNull();
        verify(commentRepository).save(any(Comment.class));
    }
}
