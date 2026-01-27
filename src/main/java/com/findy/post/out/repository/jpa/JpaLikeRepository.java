package com.findy.post.out.repository.jpa;

import com.findy.post.domain.model.like.TargetType;
import com.findy.post.out.repository.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaLikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByUserIdAndTargetIdAndTargetType(Long userId, Long targetId, TargetType targetType);
    Long countByTargetIdAndTargetType(Long targetId, TargetType targetType);
    void deleteByUserIdAndTargetIdAndTargetType(Long userId, Long targetId, TargetType targetType);
    boolean existsByUserIdAndTargetIdAndTargetType(Long userId, Long targetId, TargetType targetType);
}
