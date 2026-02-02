package com.findy.boundedcontext.user.app.usecase;

import com.findy.boundedcontext.user.app.dto.CreateUserCommand;
import com.findy.boundedcontext.user.app.interfaces.UserRepository;
import com.findy.boundedcontext.user.domain.model.User;
import com.findy.boundedcontext.user.domain.model.UserInfo;
import com.findy.boundedcontext.user.domain.model.social.SocialAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {
    private final UserRepository userRepository;

    @Transactional
    public User execute(CreateUserCommand command) {
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
}
