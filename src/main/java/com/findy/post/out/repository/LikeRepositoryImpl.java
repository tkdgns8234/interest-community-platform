package com.findy.post.out.repository;

import com.findy.post.app.interfaces.LikeRepository;
import com.findy.post.domain.model.like.Like;
import com.findy.post.domain.model.like.TargetType;
import com.findy.post.out.repository.entity.LikeEntity;
import com.findy.post.out.repository.jpa.JpaLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepository {
    private final JpaLikeRepository jpaLikeRepository;

    @Override
    public Like save(Like like) {
        LikeEntity likeEntity = new LikeEntity(like);
        likeEntity = jpaLikeRepository.save(likeEntity);
        return likeEntity.toLike();
    }

    @Override
    public Optional<Like> findByUserIdAndTarget(Long userId, Long targetId, TargetType targetType) {
        return jpaLikeRepository.findByUserIdAndTargetIdAndTargetType(userId, targetId, targetType)
                .map(LikeEntity::toLike);
    }

    @Override
    public void deleteByUserIdAndTarget(Long userId, Long targetId, TargetType targetType) {
        jpaLikeRepository.deleteByUserIdAndTargetIdAndTargetType(userId, targetId, targetType);
    }

    @Override
    public Long countByTarget(Long targetId, TargetType targetType) {
        return jpaLikeRepository.countByTargetIdAndTargetType(targetId, targetType);
    }

    @Override
    public boolean existsByUserIdAndTarget(Long userId, Long targetId, TargetType targetType) {
        return jpaLikeRepository.existsByUserIdAndTargetIdAndTargetType(userId, targetId, targetType);
    }
}
