package com.findy.user.infrastructure.repository;

import com.findy.user.application.userrelation.interfaces.UserRelationRepository;
import com.findy.user.domain.model.User;
import com.findy.user.infrastructure.repository.entity.UserEntity;
import com.findy.user.infrastructure.repository.entity.UserRelationEntity;
import com.findy.user.infrastructure.repository.jpa.JpaUserRelationRepository;
import com.findy.user.infrastructure.repository.jpa.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRelationRepositoryImpl implements UserRelationRepository {
    private final JpaUserRelationRepository jpaUserRelationRepository;
    private final JpaUserRepository jpaUserRepository;

    @Override
    public void follow(User user, User targetUser) {
        UserRelationEntity relation = new UserRelationEntity(null, user.getId(), targetUser.getId());
        jpaUserRelationRepository.save(relation);
        jpaUserRepository.saveAll(List.of(new UserEntity(user), new UserEntity(targetUser)));
    }

    @Override
    public void unfollow(User user, User targetUser) {
        jpaUserRelationRepository.deleteByUserIdAndTargetUserId(user.getId(), targetUser.getId());
        jpaUserRepository.saveAll(List.of(new UserEntity(user), new UserEntity(targetUser)));
    }

    @Override
    public boolean isFollowing(User user, User targetUser) {
        return jpaUserRelationRepository.existsByUserIdAndTargetUserId(user.getId(), targetUser.getId());
    }
}
