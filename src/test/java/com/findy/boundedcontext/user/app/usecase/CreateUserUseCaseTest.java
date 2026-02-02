package com.findy.boundedcontext.user.app.usecase;

import com.findy.boundedcontext.user.app.dto.CreateUserCommand;
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
class CreateUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateUserUseCase createUserUseCase;

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
    @DisplayName("유저 생성 성공")
    void createUserSuccess() {
        given(userRepository.save(any(User.class))).willReturn(testUser);

        CreateUserCommand command = new CreateUserCommand(
                Provider.LOCAL,
                "test@example.com",
                "password123",
                "홍길동",
                "gildong",
                "https://example.com/profile.jpg"
        );

        User createdUser = createUserUseCase.execute(command);

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getId()).isEqualTo(1L);
        assertThat(createdUser.getName()).isEqualTo("홍길동");
        assertThat(createdUser.getNickname()).isEqualTo("gildong");
        assertThat(createdUser.getProfileImageUrl()).isEqualTo("https://example.com/profile.jpg");
        verify(userRepository).save(any(User.class));
    }
}
