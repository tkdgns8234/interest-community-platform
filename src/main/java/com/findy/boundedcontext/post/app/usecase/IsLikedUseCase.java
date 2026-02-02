package com.findy.boundedcontext.post.app.usecase;

import com.findy.boundedcontext.post.app.interfaces.LikeRepository;
import com.findy.boundedcontext.post.domain.model.like.TargetType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IsLikedUseCase {
    private final LikeRepository likeRepository;

    @Transactional(readOnly = true)
    public boolean execute(Long userId, Long targetId, TargetType targetType) {
        return likeRepository.existsByUserIdAndTarget(userId, targetId, targetType);
    }
}
