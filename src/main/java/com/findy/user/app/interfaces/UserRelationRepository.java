package com.findy.user.app.interfaces;

import com.findy.user.domain.model.User;

import java.util.List;

public interface UserRelationRepository {
    void follow(User user, User targetUser);
    void unfollow(User user, User targetUser);
    boolean isFollowing(User user, User targetUser);
    List<User> findFollowers(Long userId, Long cursor, int size);
    List<User> findFollowings(Long userId, Long cursor, int size);
}
