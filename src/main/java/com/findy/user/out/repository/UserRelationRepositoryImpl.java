package com.findy.user.out.repository;

import com.findy.user.app.interfaces.UserRelationRepository;
import com.findy.user.domain.model.User;
import com.findy.user.out.repository.entity.UserEntity;
import com.findy.user.out.repository.entity.UserRelationEntity;
import com.findy.user.out.repository.jpa.JpaUserRelationRepository;
import com.findy.user.out.repository.jpa.JpaUserRepository;
import com.findy.user.out.repository.querydsl.UserRelationQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRelationRepositoryImpl implements UserRelationRepository {
    private final JpaUserRelationRepository jpaUserRelationRepository;
    private final JpaUserRepository jpaUserRepository;
    private final UserRelationQueryRepository userRelationQueryRepository;

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

    @Override
    public List<User> findFollowers(Long userId, Long cursor, int size) {
        return userRelationQueryRepository.findFollowers(userId, cursor, size)
                .stream()
                .map(UserEntity::toUser)
                .toList();
    }

    @Override
    public List<User> findFollowings(Long userId, Long cursor, int size) {
        return userRelationQueryRepository.findFollowings(userId, cursor, size)
                .stream()
                .map(UserEntity::toUser)
                .toList();
    }
}
