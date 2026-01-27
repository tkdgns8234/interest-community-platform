package com.findy.post.domain.model.comment;

import com.findy.post.domain.exception.ContentEmptyException;
import com.findy.post.domain.exception.LikeValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommentTest {

    @Nested
    @DisplayName("Comment 생성")
    class CreateComment {

        @Test
        @DisplayName("유효한 정보로 Comment를 생성할 수 있다")
        void createCommentWithValidInfo() {
            Comment comment = new Comment(1L, 10L, 100L, "댓글 내용입니다.");

            assertThat(comment.getLikeCount()).isEqualTo(0);
        }

        @Test
        @DisplayName("내용이 null이면 예외가 발생한다")
        void throwExceptionWhenContentIsNull() {
            assertThatThrownBy(() -> new Comment(1L, 10L, 100L, null))
                    .isInstanceOf(ContentEmptyException.class);
        }

        @Test
        @DisplayName("내용이 빈 문자열이면 예외가 발생한다")
        void throwExceptionWhenContentIsEmpty() {
            assertThatThrownBy(() -> new Comment(1L, 10L, 100L, ""))
                    .isInstanceOf(ContentEmptyException.class);
        }

        @Test
        @DisplayName("내용이 공백만 있으면 예외가 발생한다")
        void throwExceptionWhenContentIsWhitespace() {
            assertThatThrownBy(() -> new Comment(1L, 10L, 100L, "   "))
                    .isInstanceOf(ContentEmptyException.class);
        }
    }

    @Nested
    @DisplayName("Comment 수정")
    class UpdateComment {

        @Test
        @DisplayName("댓글 내용을 수정할 수 있다")
        void updateCommentContent() {
            Comment comment = new Comment(1L, 10L, 100L, "원래 댓글 내용");

            comment.updateContent("수정된 댓글 내용");

            assertThat(comment).isNotNull();
        }

        @Test
        @DisplayName("댓글 내용을 null로 수정하면 예외가 발생한다")
        void throwExceptionWhenUpdatingContentToNull() {
            Comment comment = new Comment(1L, 10L, 100L, "원래 댓글 내용");

            assertThatThrownBy(() -> comment.updateContent(null))
                    .isInstanceOf(ContentEmptyException.class);
        }

        @Test
        @DisplayName("댓글 내용을 빈 문자열로 수정하면 예외가 발생한다")
        void throwExceptionWhenUpdatingContentToEmpty() {
            Comment comment = new Comment(1L, 10L, 100L, "원래 댓글 내용");

            assertThatThrownBy(() -> comment.updateContent(""))
                    .isInstanceOf(ContentEmptyException.class);
        }
    }

    @Nested
    @DisplayName("좋아요")
    class Like {

        @Test
        @DisplayName("다른 사용자가 댓글에 좋아요를 누를 수 있다")
        void likeComment() {
            Comment comment = new Comment(1L, 10L, 100L, "댓글 내용");
            Long userId = 200L;

            comment.like(userId);

            assertThat(comment.getLikeCount()).isEqualTo(1);
        }

        @Test
        @DisplayName("작성자는 자신의 댓글에 좋아요를 누를 수 없다")
        void authorCannotLikeOwnComment() {
            Comment comment = new Comment(1L, 10L, 100L, "댓글 내용");
            Long authorId = 100L;

            assertThatThrownBy(() -> comment.like(authorId))
                    .isInstanceOf(LikeValidationException.class)
                    .hasMessage("Author cannot like/unlike their own");
        }

        @Test
        @DisplayName("여러 사용자가 좋아요를 누를 수 있다")
        void multipleUsersCanLike() {
            Comment comment = new Comment(1L, 10L, 100L, "댓글 내용");

            comment.like(200L);
            comment.like(201L);
            comment.like(202L);

            assertThat(comment.getLikeCount()).isEqualTo(3);
        }
    }

    @Nested
    @DisplayName("좋아요 취소")
    class Unlike {

        @Test
        @DisplayName("좋아요를 취소할 수 있다")
        void unlikeComment() {
            Comment comment = new Comment(1L, 10L, 100L, "댓글 내용");
            Long userId = 200L;

            comment.like(userId);
            comment.unlike(userId);

            assertThat(comment.getLikeCount()).isEqualTo(0);
        }

        @Test
        @DisplayName("작성자는 자신의 댓글에 좋아요 취소를 할 수 없다")
        void authorCannotUnlikeOwnComment() {
            Comment comment = new Comment(1L, 10L, 100L, "댓글 내용");
            Long authorId = 100L;

            assertThatThrownBy(() -> comment.unlike(authorId))
                    .isInstanceOf(LikeValidationException.class)
                    .hasMessage("Author cannot like/unlike their own");
        }

        @Test
        @DisplayName("좋아요 개수는 0 미만으로 내려가지 않는다")
        void likeCountDoesNotGoBelowZero() {
            Comment comment = new Comment(1L, 10L, 100L, "댓글 내용");

            comment.unlike(200L);

            assertThat(comment.getLikeCount()).isEqualTo(0);
        }
    }
}