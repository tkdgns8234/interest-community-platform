package com.findy.user.app;

import com.findy.user.app.interfaces.UserRelationRepository;
import com.findy.user.domain.model.User;
import com.findy.user.domain.service.UserRelationPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRelationService {
    private final UserService userService;
    private final UserRelationRepository userRelationRepository;
    private final UserRelationPolicy userRelationPolicy;

    public void follow(long userId, long targetUserId) {
        User user = userService.getUser(userId);
        User targetUser = userService.getUser(targetUserId);

        userRelationPolicy.validateFollow(user, targetUser);

        user.follow(targetUser);
        userRelationRepository.follow(user, targetUser);
    }

    public void unfollow(long userId, long targetUserId) {
        User user = userService.getUser(userId);
        User targetUser = userService.getUser(targetUserId);

        userRelationPolicy.validateUnfollow(user, targetUser);

        user.unfollow(targetUser);
        userRelationRepository.unfollow(user, targetUser);
    }

    public boolean isFollowing(long userId, long targetUserId) {
        User user = userService.getUser(userId);
        User targetUser = userService.getUser(targetUserId);
        return userRelationRepository.isFollowing(user, targetUser);
    }

    @Transactional(readOnly = true)
    public List<User> getFollowers(Long userId, Long cursor, int size) {
        return userRelationRepository.findFollowers(userId, cursor, size);
    }

    @Transactional(readOnly = true)
    public List<User> getFollowings(Long userId, Long cursor, int size) {
        return userRelationRepository.findFollowings(userId, cursor, size);
    }
}
