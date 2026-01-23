package com.findy.user.app;

import com.findy.user.in.web.dto.request.CreateUserRequestDTO;
import com.findy.user.app.interfaces.UserRepository;
import com.findy.user.domain.User;
import com.findy.user.domain.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    UserRepository userRepository;

    @Transactional
    public User createUser(CreateUserRequestDTO dto) {
        UserInfo userInfo = new UserInfo(dto.name(), dto.nickname(), dto.profileImageUrl());
        User user = new User(null, userInfo, null);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id);
    }
}
