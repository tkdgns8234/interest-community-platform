package com.findy.boundedcontext.post.app.usecase;

import com.findy.boundedcontext.post.app.exception.LikeAlreadyExistsException;
import com.findy.boundedcontext.post.app.interfaces.LikeRepository;
import com.findy.boundedcontext.post.domain.model.like.Like;
import com.findy.boundedcontext.post.domain.model.like.TargetType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddLikeUseCase {
    private final LikeRepository likeRepository;

    @Transactional
    public Like execute(Long userId, Long targetId, TargetType targetType) {
        if (likeRepository.existsByUserIdAndTarget(userId, targetId, targetType)) {
            throw new LikeAlreadyExistsException();
        }

        Like like = new Like(null, userId, targetId, targetType);
        return likeRepository.save(like);
    }
}
