package com.findy.boundedcontext.user.app.usecase;

import com.findy.boundedcontext.user.app.dto.UpdateUserCommand;
import com.findy.boundedcontext.user.app.interfaces.UserRepository;
import com.findy.boundedcontext.user.domain.model.User;
import com.findy.boundedcontext.user.domain.model.UserInfo;
import com.findy.boundedcontext.user.domain.model.social.Provider;
import com.findy.boundedcontext.user.domain.model.social.SocialAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UpdateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;

    private User testUser;

    @BeforeEach
    void setUp() {
        UserInfo userInfo = new UserInfo("홍길동", "gildong", "https://example.com/profile.jpg");
        SocialAccount socialAccount = SocialAccount.create(1L, "test@example.com", "password123", Provider.LOCAL, null);
        testUser = User.builder()
                .id(1L)
                .userInfo(userInfo)
                .socialAccount(socialAccount)
                .build();
    }

    @Test
    @DisplayName("유저 정보 수정 성공")
    void updateUserSuccess() {
        given(userRepository.findById(1L)).willReturn(testUser);
        given(userRepository.save(any(User.class))).willReturn(testUser);

        UpdateUserCommand command = new UpdateUserCommand(1L, "홍길동", "newNickname", "https://example.com/new.jpg");

        User updatedUser = updateUserUseCase.execute(command);

        assertThat(updatedUser).isNotNull();
        verify(userRepository).findById(1L);
        verify(userRepository).save(any(User.class));
    }
}
