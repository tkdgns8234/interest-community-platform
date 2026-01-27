package com.findy.post.app.interfaces;

import com.findy.post.domain.model.like.Like;
import com.findy.post.domain.model.like.TargetType;

import java.util.Optional;

public interface LikeRepository {
    Like save(Like like);
    Optional<Like> findByUserIdAndTarget(Long userId, Long targetId, TargetType targetType);
    void deleteByUserIdAndTarget(Long userId, Long targetId, TargetType targetType);
    Long countByTarget(Long targetId, TargetType targetType);
    boolean existsByUserIdAndTarget(Long userId, Long targetId, TargetType targetType);
}
