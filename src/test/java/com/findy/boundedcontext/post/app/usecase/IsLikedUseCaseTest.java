package com.findy.boundedcontext.post.app.usecase;

import com.findy.boundedcontext.post.app.interfaces.LikeRepository;
import com.findy.boundedcontext.post.domain.model.like.TargetType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class IsLikedUseCaseTest {

    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private IsLikedUseCase isLikedUseCase;

    @Test
    @DisplayName("좋아요 여부를 확인할 수 있다")
    void isLiked() {
        given(likeRepository.existsByUserIdAndTarget(1L, 1L, TargetType.POST)).willReturn(true);

        boolean isLiked = isLikedUseCase.execute(1L, 1L, TargetType.POST);

        assertThat(isLiked).isTrue();
    }
}
