package com.findy.boundedcontext.post.app.usecase;

import com.findy.boundedcontext.post.app.exception.LikeAlreadyExistsException;
import com.findy.boundedcontext.post.app.interfaces.LikeRepository;
import com.findy.boundedcontext.post.domain.model.like.Like;
import com.findy.boundedcontext.post.domain.model.like.TargetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AddLikeUseCaseTest {

    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private AddLikeUseCase addLikeUseCase;

    private Like testLike;

    @BeforeEach
    void setUp() {
        testLike = new Like(1L, 1L, 1L, TargetType.POST);
    }

    @Test
    @DisplayName("좋아요를 추가할 수 있다")
    void addLike() {
        given(likeRepository.existsByUserIdAndTarget(1L, 1L, TargetType.POST)).willReturn(false);
        given(likeRepository.save(any(Like.class))).willReturn(testLike);

        Like like = addLikeUseCase.execute(1L, 1L, TargetType.POST);

        assertThat(like).isNotNull();
        assertThat(like.getUserId()).isEqualTo(1L);
        assertThat(like.getTargetId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("이미 좋아요한 경우 예외가 발생한다")
    void addLikeAlreadyExists() {
        given(likeRepository.existsByUserIdAndTarget(1L, 1L, TargetType.POST)).willReturn(true);

        assertThatThrownBy(() -> addLikeUseCase.execute(1L, 1L, TargetType.POST))
                .isInstanceOf(LikeAlreadyExistsException.class);
    }
}
