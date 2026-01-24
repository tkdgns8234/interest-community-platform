package com.findy.user.out.repository.jpa;

import com.findy.user.out.repository.entity.UserRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRelationRepository extends JpaRepository<UserRelationEntity, Long> {
    boolean existsByUserIdAndTargetUserId(Long userId, Long targetUserId);
    void deleteByUserIdAndTargetUserId(Long userId, Long targetUserId);
}
