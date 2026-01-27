package com.findy.notification.out.interfaces;

import com.findy.user.app.interfaces.UserRelationRepository;
import com.findy.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserRelationEntryPointImpl implements UserRelationEntryPoint {
    UserRelationRepository userRelationRepository;

    @Override
    public List<Long> findFollowers(Long userId, Long cursor, int pageSize) {
        List<User> followers = userRelationRepository.findFollowers(
                userId,
                cursor,
                pageSize
        );

        if (followers.isEmpty()) {
            return null;
        }

        return followers.stream()
                .map(User::getId)
                .collect(Collectors.toList());
    }
}
