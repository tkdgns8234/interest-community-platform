package com.findy.boundedcontext.post.app.usecase;

import com.findy.boundedcontext.post.app.exception.LikeNotFoundException;
import com.findy.boundedcontext.post.app.interfaces.LikeRepository;
import com.findy.boundedcontext.post.domain.model.like.TargetType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RemoveLikeUseCase {
    private final LikeRepository likeRepository;

    @Transactional
    public void execute(Long userId, Long targetId, TargetType targetType) {
        if (!likeRepository.existsByUserIdAndTarget(userId, targetId, targetType)) {
            throw new LikeNotFoundException();
        }

        likeRepository.deleteByUserIdAndTarget(userId, targetId, targetType);
    }
}
