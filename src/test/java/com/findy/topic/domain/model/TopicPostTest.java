package com.findy.topic.domain.model;

import com.findy.common.domain.PostInfo;
import com.findy.post.domain.exception.LikeValidationException;
import com.findy.topic.domain.model.post.TopicPost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TopicPostTest {

    private PostInfo postInfo;

    @BeforeEach
    void setUp() {
        postInfo = new PostInfo("테스트 제목", "테스트 내용입니다.");
    }

    @Nested
    @DisplayName("TopicPost 생성")
    class CreateTopicPost {

        @Test
        @DisplayName("유효한 정보로 TopicPost를 생성할 수 있다")
        void createTopicPostWithValidInfo() {
            TopicPost post = new TopicPost(1L, 10L, 100L, postInfo);

            assertThat(post.getId()).isEqualTo(1L);
            assertThat(post.getTopicId()).isEqualTo(10L);
            assertThat(post.getAuthorId()).isEqualTo(100L);
            assertThat(post.getTitle()).isEqualTo("테스트 제목");
            assertThat(post.getContent()).isEqualTo("테스트 내용입니다.");
        }

        @Test
        @DisplayName("TopicPost 생성 시 LikeManager가 초기화된다")
        void likeManagerIsInitializedOnCreation() {
            TopicPost post = new TopicPost(1L, 10L, 100L, postInfo);

            assertThat(post.getLikeManager()).isNotNull();
            assertThat(post.getLikeCount()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("작성자 확인")
    class AuthorCheck {

        @Test
        @DisplayName("작성자를 확인할 수 있다")
        void checkAuthor() {
            TopicPost post = new TopicPost(1L, 10L, 100L, postInfo);

            assertThat(post.isAuthor(100L)).isTrue();
            assertThat(post.isAuthor(200L)).isFalse();
        }
    }

    @Nested
    @DisplayName("TopicPost 내용 수정")
    class UpdateTopicPost {

        @Test
        @DisplayName("제목을 수정할 수 있다")
        void updateTitle() {
            TopicPost post = new TopicPost(1L, 10L, 100L, postInfo);

            post.updateTitle("수정된 제목");

            assertThat(post.getTitle()).isEqualTo("수정된 제목");
        }

        @Test
        @DisplayName("내용을 수정할 수 있다")
        void updateContent() {
            TopicPost post = new TopicPost(1L, 10L, 100L, postInfo);

            post.updateContent("수정된 내용입니다.");

            assertThat(post.getContent()).isEqualTo("수정된 내용입니다.");
        }
    }

    @Nested
    @DisplayName("좋아요")
    class Like {

        @Test
        @DisplayName("다른 사용자가 게시물에 좋아요를 누를 수 있다")
        void likePost() {
            TopicPost post = new TopicPost(1L, 10L, 100L, postInfo);
            Long userId = 200L;

            post.like(userId);

            assertThat(post.getLikeCount()).isEqualTo(1);
        }

        @Test
        @DisplayName("작성자는 자신의 게시물에 좋아요를 누를 수 없다")
        void authorCannotLikeOwnPost() {
            TopicPost post = new TopicPost(1L, 10L, 100L, postInfo);
            Long authorId = 100L;

            assertThatThrownBy(() -> post.like(authorId))
                    .isInstanceOf(LikeValidationException.class)
                    .hasMessage("Author cannot like/unlike their own");
        }

        @Test
        @DisplayName("여러 사용자가 좋아요를 누를 수 있다")
        void multipleUsersCanLike() {
            TopicPost post = new TopicPost(1L, 10L, 100L, postInfo);

            post.like(200L);
            post.like(201L);
            post.like(202L);

            assertThat(post.getLikeCount()).isEqualTo(3);
        }
    }

    @Nested
    @DisplayName("좋아요 취소")
    class Unlike {

        @Test
        @DisplayName("좋아요를 취소할 수 있다")
        void unlikePost() {
            TopicPost post = new TopicPost(1L, 10L, 100L, postInfo);
            Long userId = 200L;

            post.like(userId);
            post.unlike(userId);

            assertThat(post.getLikeCount()).isEqualTo(0);
        }

        @Test
        @DisplayName("작성자는 자신의 게시물에 좋아요 취소를 할 수 없다")
        void authorCannotUnlikeOwnPost() {
            TopicPost post = new TopicPost(1L, 10L, 100L, postInfo);
            Long authorId = 100L;

            assertThatThrownBy(() -> post.unlike(authorId))
                    .isInstanceOf(LikeValidationException.class)
                    .hasMessage("Author cannot like/unlike their own");
        }

        @Test
        @DisplayName("좋아요 개수는 0 미만으로 내려가지 않는다")
        void likeCountDoesNotGoBelowZero() {
            TopicPost post = new TopicPost(1L, 10L, 100L, postInfo);

            post.unlike(200L);

            assertThat(post.getLikeCount()).isEqualTo(0);
        }
    }
}
