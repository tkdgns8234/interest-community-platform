package com.findy.boundedcontext.user.app.usecase;

import com.findy.boundedcontext.user.app.interfaces.UserRepository;
import com.findy.boundedcontext.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllUsersUseCase {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> execute(Long cursor, int size) {
        return userRepository.findAll(cursor, size);
    }
}
