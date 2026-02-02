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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetCommentsByPostIdUseCaseTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private GetCommentsByPostIdUseCase getCommentsByPostIdUseCase;

    private Comment testComment;

    @BeforeEach
    void setUp() {
        testComment = new Comment(1L, 1L, 1L, "테스트 댓글");
    }

    @Test
    @DisplayName("게시글 ID로 댓글 목록을 조회할 수 있다")
    void getCommentsByPostId() {
        given(commentRepository.findByPostId(1L)).willReturn(List.of(testComment));

        List<Comment> comments = getCommentsByPostIdUseCase.execute(1L);

        assertThat(comments).hasSize(1);
        assertThat(comments.get(0).getId()).isEqualTo(1L);
    }
}
