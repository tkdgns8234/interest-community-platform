package com.findy.boundedcontext.user.app.usecase;

import com.findy.boundedcontext.user.app.interfaces.UserRelationRepository;
import com.findy.boundedcontext.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetFollowingsUseCase {
    private final UserRelationRepository userRelationRepository;

    @Transactional(readOnly = true)
    public List<User> execute(Long userId, Long cursor, int size) {
        return userRelationRepository.findFollowings(userId, cursor, size);
    }
}
