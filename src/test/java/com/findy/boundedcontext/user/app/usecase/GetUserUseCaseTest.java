package com.findy.boundedcontext.user.app.usecase;

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
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserUseCase getUserUseCase;

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
    @DisplayName("ID로 유저 조회 성공")
    void getUserSuccess() {
        given(userRepository.findById(1L)).willReturn(testUser);

        User user = getUserUseCase.execute(1L);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("홍길동");
        assertThat(user.getNickname()).isEqualTo("gildong");
        assertThat(user.getEmail()).isEqualTo("test@example.com");
        assertThat(user.getProfileImageUrl()).isEqualTo("https://example.com/profile.jpg");
    }
}
