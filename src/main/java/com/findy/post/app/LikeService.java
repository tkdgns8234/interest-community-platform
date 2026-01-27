package com.findy.post.app;

import com.findy.post.app.exception.LikeAlreadyExistsException;
import com.findy.post.app.exception.LikeNotFoundException;
import com.findy.post.app.interfaces.LikeRepository;
import com.findy.post.domain.model.like.Like;
import com.findy.post.domain.model.like.TargetType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    @Transactional
    public Like addLike(Long userId, Long targetId, TargetType targetType) {
        if (likeRepository.existsByUserIdAndTarget(userId, targetId, targetType)) {
            throw new LikeAlreadyExistsException();
        }

        Like like = new Like(null, userId, targetId, targetType);
        return likeRepository.save(like);
    }

    @Transactional
    public void removeLike(Long userId, Long targetId, TargetType targetType) {
        if (!likeRepository.existsByUserIdAndTarget(userId, targetId, targetType)) {
            throw new LikeNotFoundException();
        }

        likeRepository.deleteByUserIdAndTarget(userId, targetId, targetType);
    }

    @Transactional(readOnly = true)
    public Long getLikeCount(Long targetId, TargetType targetType) {
        return likeRepository.countByTarget(targetId, targetType);
    }

    @Transactional(readOnly = true)
    public boolean isLiked(Long userId, Long targetId, TargetType targetType) {
        return likeRepository.existsByUserIdAndTarget(userId, targetId, targetType);
    }
}
