package com.findy.boundedcontext.user.app.usecase;

import com.findy.boundedcontext.user.app.dto.UpdateUserCommand;
import com.findy.boundedcontext.user.app.interfaces.UserRepository;
import com.findy.boundedcontext.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCase {
    private final UserRepository userRepository;

    @Transactional
    public User execute(UpdateUserCommand command) {
        User user = userRepository.findById(command.id());
        user.updateUserInfo(command.nickname(), command.profileImageUrl());
        return userRepository.save(user);
    }
}
