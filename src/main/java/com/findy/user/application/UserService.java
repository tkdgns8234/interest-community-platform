package com.findy.user.application;

import com.findy.user.application.dto.GetUserResponseDTO;
import com.findy.user.application.interfaces.UserRepository;
import com.findy.user.domain.User;
import com.findy.user.domain.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    UserRepository userRepository;

    public User createUser(String name, String nickname, String profileImageUrl) {
        UserInfo userInfo = new UserInfo(name, nickname, profileImageUrl);
        User user = new User(null, userInfo, null);
        return userRepository.save(user);
    }

    public GetUserResponseDTO getUserProfile(Integer id) {
        User user = getUser(id);
        return new GetUserResponseDTO(user);
    }

    public User getUser(Integer id) {
        return userRepository.findById();
    }
}
