package com.findy.post.domain.model;

import com.findy.post.domain.exception.LikeValidationException;
import com.findy.post.domain.model.post.Post;
import com.findy.post.domain.model.post.PostInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostTest {

    private PostInfo postInfo;

    @BeforeEach
    void setUp() {
        postInfo = new PostInfo("테스트 제목", "테스트 내용입니다.");
    }

    @Nested
    @DisplayName("Post 생성")
    class CreatePost {

        @Test
        @DisplayName("유효한 정보로 Post를 생성할 수 있다")
        void createPostWithValidInfo() {
            Post post = new Post(1L, 100L, postInfo);

            assertThat(post.getId()).isEqualTo(1L);
            assertThat(post.getAuthorId()).isEqualTo(100L);
            assertThat(post.getPostInfo().getTitle()).isEqualTo("테스트 제목");
            assertThat(post.getPostInfo().getContent()).isEqualTo("테스트 내용입니다.");
        }

        @Test
        @DisplayName("Post 생성 시 LikeManager가 초기화된다")
        void likeManagerIsInitializedOnCreation() {
            Post post = new Post(1L, 100L, postInfo);

            assertThat(post.getLikeManager()).isNotNull();
            assertThat(post.getLikeManager().getCount()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("Post 내용 수정")
    class UpdatePost {

        @Test
        @DisplayName("제목을 수정할 수 있다")
        void updateTitle() {
            Post post = new Post(1L, 100L, postInfo);

            post.setTitle("수정된 제목");

            assertThat(post.getPostInfo().getTitle()).isEqualTo("수정된 제목");
        }

        @Test
        @DisplayName("내용을 수정할 수 있다")
        void updateContent() {
            Post post = new Post(1L, 100L, postInfo);

            post.setContent("수정된 내용입니다.");

            assertThat(post.getPostInfo().getContent()).isEqualTo("수정된 내용입니다.");
        }
    }

    @Nested
    @DisplayName("좋아요")
    class Like {

        @Test
        @DisplayName("다른 사용자가 게시물에 좋아요를 누를 수 있다")
        void likePost() {
            Post post = new Post(1L, 100L, postInfo);
            Long userId = 200L;

            post.like(userId);

            assertThat(post.getLikeManager().getCount()).isEqualTo(1);
        }

        @Test
        @DisplayName("작성자는 자신의 게시물에 좋아요를 누를 수 없다")
        void authorCannotLikeOwnPost() {
            Post post = new Post(1L, 100L, postInfo);
            Long authorId = 100L;

            assertThatThrownBy(() -> post.like(authorId))
                    .isInstanceOf(LikeValidationException.class)
                    .hasMessage("Author cannot like/unlike their own");
        }

        @Test
        @DisplayName("여러 사용자가 좋아요를 누를 수 있다")
        void multipleUsersCanLike() {
            Post post = new Post(1L, 100L, postInfo);

            post.like(200L);
            post.like(201L);
            post.like(202L);

            assertThat(post.getLikeManager().getCount()).isEqualTo(3);
        }
    }

    @Nested
    @DisplayName("좋아요 취소")
    class Unlike {

        @Test
        @DisplayName("좋아요를 취소할 수 있다")
        void unlikePost() {
            Post post = new Post(1L, 100L, postInfo);
            Long userId = 200L;

            post.like(userId);
            post.unlike(userId);

            assertThat(post.getLikeManager().getCount()).isEqualTo(0);
        }

        @Test
        @DisplayName("작성자는 자신의 게시물에 좋아요 취소를 할 수 없다")
        void authorCannotUnlikeOwnPost() {
            Post post = new Post(1L, 100L, postInfo);
            Long authorId = 100L;

            assertThatThrownBy(() -> post.unlike(authorId))
                    .isInstanceOf(LikeValidationException.class)
                    .hasMessage("Author cannot like/unlike their own");
        }

        @Test
        @DisplayName("좋아요 개수는 0 미만으로 내려가지 않는다")
        void likeCountDoesNotGoBelowZero() {
            Post post = new Post(1L, 100L, postInfo);

            post.unlike(200L);

            assertThat(post.getLikeManager().getCount()).isEqualTo(0);
        }
    }
}
