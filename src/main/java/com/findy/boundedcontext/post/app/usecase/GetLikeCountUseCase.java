package com.findy.boundedcontext.post.app.usecase;

import com.findy.boundedcontext.post.app.interfaces.LikeRepository;
import com.findy.boundedcontext.post.domain.model.like.TargetType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetLikeCountUseCase {
    private final LikeRepository likeRepository;

    @Transactional(readOnly = true)
    public Long execute(Long targetId, TargetType targetType) {
        return likeRepository.countByTarget(targetId, targetType);
    }
}
