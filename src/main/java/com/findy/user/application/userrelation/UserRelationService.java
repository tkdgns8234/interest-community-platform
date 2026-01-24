package com.findy.user.application.userrelation;

import com.findy.user.application.userrelation.exception.UserAlreadyFollowException;
import com.findy.user.application.userrelation.exception.UserNotFollowedException;
import com.findy.user.application.userrelation.interfaces.UserRelationRepository;
import com.findy.user.application.user.UserService;
import com.findy.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRelationService {
    private final UserService userService;
    private final UserRelationRepository userRelationRepository;

    public void follow(long userId, long targetUserId) {
        User user = userService.getUserById(userId);
        User targetUser = userService.getUserById(targetUserId);

        if (isFollowing(userId, targetUserId)) {
            throw new UserAlreadyFollowException();
        }

        user.follow(targetUser);
        userRelationRepository.follow(user, targetUser);
    }

    public void unfollow(long userId, long targetUserId) {
        User user = userService.getUserById(userId);
        User targetUser = userService.getUserById(targetUserId);

        if (!isFollowing(userId, targetUserId)) {
            throw new UserNotFollowedException();
        }

        user.unfollow(targetUser);
        userRelationRepository.unfollow(user, targetUser);
    }

    public boolean isFollowing(long userId, long targetUserId) {
        User user = userService.getUserById(userId);
        User targetUser = userService.getUserById(targetUserId);

        return userRelationRepository.isFollowing(user, targetUser);
    }
}
