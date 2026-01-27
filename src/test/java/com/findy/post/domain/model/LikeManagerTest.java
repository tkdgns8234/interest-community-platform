package com.findy.post.domain.model;

import com.findy.post.domain.exception.LikeValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LikeManagerTest {

    @Nested
    @DisplayName("LikeManager 생성")
    class CreateLikeManager {

        @Test
        @DisplayName("LikeManager를 생성하면 초기 카운트는 0이다")
        void initialCountIsZero() {
            LikeManager likeManager = new LikeManager();

            assertThat(likeManager.getCount()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("좋아요")
    class Like {

        @Test
        @DisplayName("좋아요를 추가하면 카운트가 증가한다")
        void likeIncreasesCount() {
            LikeManager likeManager = new LikeManager();
            Long authorId = 100L;
            Long userId = 200L;

            likeManager.like(authorId, userId);

            assertThat(likeManager.getCount()).isEqualTo(1);
        }

        @Test
        @DisplayName("작성자가 자신의 콘텐츠에 좋아요를 누르면 예외가 발생한다")
        void throwExceptionWhenAuthorLikesOwnContent() {
            LikeManager likeManager = new LikeManager();
            Long authorId = 100L;

            assertThatThrownBy(() -> likeManager.like(authorId, authorId))
                    .isInstanceOf(LikeValidationException.class)
                    .hasMessage("Author cannot like/unlike their own");
        }

        @Test
        @DisplayName("여러 번 좋아요를 누르면 카운트가 계속 증가한다")
        void multipleLikesIncreaseCount() {
            LikeManager likeManager = new LikeManager();
            Long authorId = 100L;

            likeManager.like(authorId, 200L);
            likeManager.like(authorId, 201L);
            likeManager.like(authorId, 202L);

            assertThat(likeManager.getCount()).isEqualTo(3);
        }
    }

    @Nested
    @DisplayName("좋아요 취소")
    class Unlike {

        @Test
        @DisplayName("좋아요를 취소하면 카운트가 감소한다")
        void unlikeDecreasesCount() {
            LikeManager likeManager = new LikeManager();
            Long authorId = 100L;
            Long userId = 200L;

            likeManager.like(authorId, userId);
            likeManager.unlike(authorId, userId);

            assertThat(likeManager.getCount()).isEqualTo(0);
        }

        @Test
        @DisplayName("작성자가 자신의 콘텐츠에 좋아요 취소를 하면 예외가 발생한다")
        void throwExceptionWhenAuthorUnlikesOwnContent() {
            LikeManager likeManager = new LikeManager();
            Long authorId = 100L;

            assertThatThrownBy(() -> likeManager.unlike(authorId, authorId))
                    .isInstanceOf(LikeValidationException.class)
                    .hasMessage("Author cannot like/unlike their own");
        }

        @Test
        @DisplayName("카운트가 0일 때 좋아요 취소를 해도 0 미만으로 내려가지 않는다")
        void countDoesNotGoBelowZero() {
            LikeManager likeManager = new LikeManager();
            Long authorId = 100L;

            likeManager.unlike(authorId, 200L);

            assertThat(likeManager.getCount()).isEqualTo(0);
        }
    }
}