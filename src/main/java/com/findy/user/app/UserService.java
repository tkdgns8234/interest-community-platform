package com.findy.user.app;

import com.findy.user.app.interfaces.UserRepository;
import com.findy.user.domain.model.social.SocialAccount;
import com.findy.user.in.rest.request.CreateUserRequestDTO;
import com.findy.user.domain.model.User;
import com.findy.user.domain.model.UserInfo;
import com.findy.user.in.rest.request.UpdateUserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User createUser(CreateUserRequestDTO dto) {
        UserInfo userInfo = new UserInfo(dto.name(), dto.nickname(), dto.profileImageUrl());
        SocialAccount socialAccount = SocialAccount.create(
                null,
                dto.email(),
                dto.password(),
                dto.provider(),
                null
        );

        User user = new User(null, userInfo, socialAccount);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User updateUserInfo(UpdateUserRequestDTO dto) {
        User user = userRepository.findById(dto.id());
        user.updateUserInfo(dto.nickname(), dto.profileImageUrl());

        return userRepository.save(user);
    }
}
