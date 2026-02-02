package com.findy.boundedcontext.post.app.usecase;

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
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetCommentUseCaseTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private GetCommentUseCase getCommentUseCase;

    private Comment testComment;

    @BeforeEach
    void setUp() {
        testComment = new Comment(1L, 1L, 1L, "테스트 댓글");
    }

    @Test
    @DisplayName("ID로 댓글을 조회할 수 있다")
    void getComment() {
        given(commentRepository.findById(1L)).willReturn(testComment);

        Comment comment = getCommentUseCase.execute(1L);

        assertThat(comment).isNotNull();
        assertThat(comment.getId()).isEqualTo(1L);
        assertThat(comment.getContent()).isEqualTo("테스트 댓글");
    }
}
