package com.findy.boundedcontext.user.app.interfaces;

import com.findy.boundedcontext.user.domain.model.User;

import java.util.List;

public interface UserRepository {
    User findById(long id);
    User save(User user);
    Long getUserFollowerCount(long userId);
    List<User> findAll(Long cursor, int size);
}
