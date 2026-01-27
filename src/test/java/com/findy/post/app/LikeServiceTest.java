package com.findy.post.app;

import com.findy.post.app.exception.LikeAlreadyExistsException;
import com.findy.post.app.exception.LikeNotFoundException;
import com.findy.post.app.interfaces.LikeRepository;
import com.findy.post.domain.model.like.Like;
import com.findy.post.domain.model.like.TargetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private LikeService likeService;

    private Like testLike;

    @BeforeEach
    void setUp() {
        testLike = new Like(1L, 1L, 1L, TargetType.POST);
    }

    @Nested
    @DisplayName("좋아요 추가")
    class AddLike {

        @Test
        @DisplayName("좋아요를 추가할 수 있다")
        void addLike() {
            given(likeRepository.existsByUserIdAndTarget(1L, 1L, TargetType.POST)).willReturn(false);
            given(likeRepository.save(any(Like.class))).willReturn(testLike);

            Like like = likeService.addLike(1L, 1L, TargetType.POST);

            assertThat(like).isNotNull();
            assertThat(like.getUserId()).isEqualTo(1L);
            assertThat(like.getTargetId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("이미 좋아요한 경우 예외가 발생한다")
        void addLikeAlreadyExists() {
            given(likeRepository.existsByUserIdAndTarget(1L, 1L, TargetType.POST)).willReturn(true);

            assertThatThrownBy(() -> likeService.addLike(1L, 1L, TargetType.POST))
                    .isInstanceOf(LikeAlreadyExistsException.class);
        }
    }

    @Nested
    @DisplayName("좋아요 취소")
    class RemoveLike {

        @Test
        @DisplayName("좋아요를 취소할 수 있다")
        void removeLike() {
            given(likeRepository.existsByUserIdAndTarget(1L, 1L, TargetType.POST)).willReturn(true);

            likeService.removeLike(1L, 1L, TargetType.POST);

            verify(likeRepository).deleteByUserIdAndTarget(1L, 1L, TargetType.POST);
        }

        @Test
        @DisplayName("좋아요하지 않은 경우 예외가 발생한다")
        void removeLikeNotFound() {
            given(likeRepository.existsByUserIdAndTarget(1L, 1L, TargetType.POST)).willReturn(false);

            assertThatThrownBy(() -> likeService.removeLike(1L, 1L, TargetType.POST))
                    .isInstanceOf(LikeNotFoundException.class);
        }
    }

    @Nested
    @DisplayName("좋아요 조회")
    class GetLike {

        @Test
        @DisplayName("좋아요 개수를 조회할 수 있다")
        void getLikeCount() {
            given(likeRepository.countByTarget(1L, TargetType.POST)).willReturn(5L);

            Long count = likeService.getLikeCount(1L, TargetType.POST);

            assertThat(count).isEqualTo(5L);
        }

        @Test
        @DisplayName("좋아요 여부를 확인할 수 있다")
        void isLiked() {
            given(likeRepository.existsByUserIdAndTarget(1L, 1L, TargetType.POST)).willReturn(true);

            boolean isLiked = likeService.isLiked(1L, 1L, TargetType.POST);

            assertThat(isLiked).isTrue();
        }
    }
}
