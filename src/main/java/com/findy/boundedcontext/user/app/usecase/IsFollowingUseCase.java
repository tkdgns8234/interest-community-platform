package com.findy.boundedcontext.user.app.usecase;

import com.findy.boundedcontext.user.app.interfaces.UserRelationRepository;
import com.findy.boundedcontext.user.app.interfaces.UserRepository;
import com.findy.boundedcontext.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IsFollowingUseCase {
    private final UserRepository userRepository;
    private final UserRelationRepository userRelationRepository;

    @Transactional(readOnly = true)
    public boolean execute(Long userId, Long targetUserId) {
        User user = userRepository.findById(userId);
        User targetUser = userRepository.findById(targetUserId);

        return userRelationRepository.isFollowing(user, targetUser);
    }
}
