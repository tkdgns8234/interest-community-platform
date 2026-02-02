package com.findy.boundedcontext.user.app.usecase;

import com.findy.boundedcontext.user.app.interfaces.UserRelationRepository;
import com.findy.boundedcontext.user.app.interfaces.UserRepository;
import com.findy.boundedcontext.user.domain.model.User;
import com.findy.boundedcontext.user.domain.service.UserRelationPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UnfollowUserUseCase {
    private final UserRepository userRepository;
    private final UserRelationRepository userRelationRepository;
    private final UserRelationPolicy userRelationPolicy;

    @Transactional
    public void execute(Long userId, Long targetUserId) {
        User user = userRepository.findById(userId);
        User targetUser = userRepository.findById(targetUserId);

        userRelationPolicy.validateUnfollow(user, targetUser);

        user.unfollow(targetUser);
        userRelationRepository.unfollow(user, targetUser);
    }
}
