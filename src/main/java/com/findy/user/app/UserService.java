package com.findy.user.app;

import com.findy.user.app.dto.CreateUserCommand;
import com.findy.user.app.dto.UpdateUserCommand;
import com.findy.user.app.interfaces.UserRepository;
import com.findy.user.domain.model.social.SocialAccount;
import com.findy.user.domain.model.User;
import com.findy.user.domain.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User createUser(CreateUserCommand command) {
        UserInfo userInfo = new UserInfo(
                command.name(),
                command.nickname(),
                command.profileImageUrl()
        );
        SocialAccount socialAccount = SocialAccount.create(
                null,
                command.email(),
                command.password(),
                command.provider(),
                null
        );

        User user = new User(null, userInfo, socialAccount);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUser(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User updateUserInfo(UpdateUserCommand command) {
        User user = userRepository.findById(command.id());
        user.updateUserInfo(command.nickname(), command.profileImageUrl());

        return userRepository.save(user);
    }
}
