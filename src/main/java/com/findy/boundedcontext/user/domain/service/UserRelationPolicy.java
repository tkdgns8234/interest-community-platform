package com.findy.boundedcontext.user.domain.service;

import com.findy.boundedcontext.user.app.exception.UserAlreadyFollowException;
import com.findy.boundedcontext.user.app.exception.UserNotFollowedException;
import com.findy.boundedcontext.user.app.interfaces.UserRelationRepository;
import com.findy.boundedcontext.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRelationPolicy {
    private final UserRelationRepository userRelationRepository;

    public void validateFollow(User user, User targetUser) {
        if (userRelationRepository.isFollowing(user, targetUser)) {
            throw new UserAlreadyFollowException();
        }
    }

    public void validateUnfollow(User user, User targetUser) {
        if (!userRelationRepository.isFollowing(user, targetUser)) {
            throw new UserNotFollowedException();
        }
    }
}