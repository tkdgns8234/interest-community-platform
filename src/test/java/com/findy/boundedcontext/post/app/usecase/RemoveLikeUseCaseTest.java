package com.findy.boundedcontext.post.app.usecase;

import com.findy.boundedcontext.post.app.exception.LikeNotFoundException;
import com.findy.boundedcontext.post.app.interfaces.LikeRepository;
import com.findy.boundedcontext.post.domain.model.like.TargetType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RemoveLikeUseCaseTest {

    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private RemoveLikeUseCase removeLikeUseCase;

    @Test
    @DisplayName("좋아요를 취소할 수 있다")
    void removeLike() {
        given(likeRepository.existsByUserIdAndTarget(1L, 1L, TargetType.POST)).willReturn(true);

        removeLikeUseCase.execute(1L, 1L, TargetType.POST);

        verify(likeRepository).deleteByUserIdAndTarget(1L, 1L, TargetType.POST);
    }

    @Test
    @DisplayName("좋아요하지 않은 경우 예외가 발생한다")
    void removeLikeNotFound() {
        given(likeRepository.existsByUserIdAndTarget(1L, 1L, TargetType.POST)).willReturn(false);

        assertThatThrownBy(() -> removeLikeUseCase.execute(1L, 1L, TargetType.POST))
                .isInstanceOf(LikeNotFoundException.class);
    }
}
