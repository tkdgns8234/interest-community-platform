package com.findy.post.app;

import com.findy.post.app.dto.CreateCommentCommand;
import com.findy.post.app.dto.UpdateCommentCommand;
import com.findy.post.app.interfaces.CommentRepository;
import com.findy.post.domain.model.comment.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    private Comment testComment;

    @BeforeEach
    void setUp() {
        testComment = new Comment(1L, 1L, 1L, "테스트 댓글");
    }

    @Nested
    @DisplayName("댓글 생성")
    class CreateComment {

        @Test
        @DisplayName("댓글을 생성할 수 있다")
        void createComment() {
            given(commentRepository.save(any(Comment.class))).willReturn(testComment);

            CreateCommentCommand command = new CreateCommentCommand(1L, 1L, "테스트 댓글");
            Comment createdComment = commentService.createComment(command);

            assertThat(createdComment).isNotNull();
            assertThat(createdComment.getId()).isEqualTo(1L);
            assertThat(createdComment.getContent()).isEqualTo("테스트 댓글");
        }
    }

    @Nested
    @DisplayName("댓글 조회")
    class GetComment {

        @Test
        @DisplayName("ID로 댓글을 조회할 수 있다")
        void getComment() {
            given(commentRepository.findById(1L)).willReturn(testComment);

            Comment comment = commentService.getComment(1L);

            assertThat(comment).isNotNull();
            assertThat(comment.getId()).isEqualTo(1L);
            assertThat(comment.getContent()).isEqualTo("테스트 댓글");
        }

        @Test
        @DisplayName("게시글 ID로 댓글 목록을 조회할 수 있다")
        void getCommentsByPostId() {
            given(commentRepository.findByPostId(1L)).willReturn(List.of(testComment));

            List<Comment> comments = commentService.getCommentsByPostId(1L);

            assertThat(comments).hasSize(1);
            assertThat(comments.get(0).getId()).isEqualTo(1L);
        }
    }

    @Nested
    @DisplayName("댓글 수정")
    class UpdateComment {

        @Test
        @DisplayName("댓글을 수정할 수 있다")
        void updateComment() {
            given(commentRepository.findById(1L)).willReturn(testComment);
            given(commentRepository.save(any(Comment.class))).willReturn(testComment);

            UpdateCommentCommand command = new UpdateCommentCommand(1L, "수정된 댓글");
            Comment updatedComment = commentService.updateComment(command);

            assertThat(updatedComment).isNotNull();
            verify(commentRepository).save(any(Comment.class));
        }
    }

    @Nested
    @DisplayName("댓글 삭제")
    class DeleteComment {

        @Test
        @DisplayName("댓글을 삭제할 수 있다")
        void deleteComment() {
            commentService.deleteComment(1L);

            verify(commentRepository).deleteById(1L);
        }
    }
}
