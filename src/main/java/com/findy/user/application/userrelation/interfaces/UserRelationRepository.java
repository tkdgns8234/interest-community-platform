package com.findy.user.application.userrelation.interfaces;

import com.findy.user.domain.model.User;

public interface UserRelationRepository {
    void follow(User user, User targetUser);
    void unfollow(User user, User targetUser);
    boolean isFollowing(User user, User targetUser);
}
