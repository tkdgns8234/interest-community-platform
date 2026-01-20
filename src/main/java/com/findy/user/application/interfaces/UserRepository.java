package com.findy.user.application.interfaces;

import com.findy.user.domain.User;

public interface UserRepository {
    User findById();
    User save(User user);
}
