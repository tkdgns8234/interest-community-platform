package com.findy.user.app;

import com.findy.user.app.dto.CreateUserCommand;
import com.findy.user.app.interfaces.UserRepository;
import com.findy.user.domain.model.User;
import com.findy.user.domain.model.UserInfo;
import com.findy.user.domain.model.social.Provider;
import com.findy.user.domain.model.social.SocialAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

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

    @Nested
    @DisplayName("유저 생성")
    class CreateUser {

        @Test
        @DisplayName("유저 생성 테스트")
        void createUserWithoutSocialAccount() {
            given(userRepository.save(any(User.class))).willReturn(testUser);

            CreateUserCommand command = new CreateUserCommand(
                    Provider.LOCAL,
                    "test@example.com",
                    "password123",
                    "홍길동",
                    "gildong",
                    "https://example.com/profile.jpg"
            );

            User createdUser = userService.createUser(command);

            assertThat(createdUser).isNotNull();
            assertThat(createdUser.getId()).isEqualTo(1L);
            assertThat(createdUser.getName()).isEqualTo("홍길동");
            assertThat(createdUser.getNickname()).isEqualTo("gildong");
            assertThat(createdUser.getProfileImageUrl()).isEqualTo("https://example.com/profile.jpg");
        }
    }

    @Nested
    @DisplayName("유저 프로필 조회")
    class GetUserProfile {

        @Test
        @DisplayName("ID로 유저 프로필을 조회할 수 있다")
        void getUserProfileById() {
            given(userRepository.findById(1L)).willReturn(testUser);

            User user = userService.getUser(1L);

            assertThat(user).isNotNull();
            assertThat(user.getId()).isEqualTo(1L);
            assertThat(user.getName()).isEqualTo("홍길동");
            assertThat(user.getNickname()).isEqualTo("gildong");
            assertThat(user.getEmail()).isEqualTo("test@example.com");
            assertThat(user.getProfileImageUrl()).isEqualTo("https://example.com/profile.jpg");
        }
    }
}