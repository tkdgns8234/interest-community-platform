package com.findy.user.out.repository;

import com.findy.user.app.exception.UserNotFoundException;
import com.findy.user.app.interfaces.UserRepository;
import com.findy.user.domain.model.User;
import com.findy.user.out.repository.entity.UserEntity;
import com.findy.user.out.repository.jpa.JpaUserRepository;
import com.findy.user.out.repository.querydsl.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaUserRepository;
    private final UserQueryRepository userQueryRepository;

    @Override
    public User findById(long id) {
        UserEntity userEntity = jpaUserRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return userEntity.toUser();
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = new UserEntity(user);
        userEntity = jpaUserRepository.save(userEntity);
        return userEntity.toUser();
    }

    @Override
    public Long getUserFollowerCount(long userId) {
        UserEntity userEntity = jpaUserRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return userEntity.getFollowerCount();
    }

    @Override
    public List<User> findAll(Long cursor, int size) {
        return userQueryRepository.findAll(cursor, size)
                .stream()
                .map(UserEntity::toUser)
                .toList();
    }
}
