package com.findy.user.infrastructure.repository;

import com.findy.user.application.user.exception.UserNotFoundException;
import com.findy.user.application.user.interfaces.UserRepository;
import com.findy.user.domain.model.User;
import com.findy.user.infrastructure.repository.entity.UserEntity;
import com.findy.user.infrastructure.repository.jpa.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository jpaUserRepository;

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
}
