package com.findy.user.app.interfaces;

import com.findy.user.domain.model.User;

public interface UserRepository {
    User findById(long id);
    User save(User user);
}
