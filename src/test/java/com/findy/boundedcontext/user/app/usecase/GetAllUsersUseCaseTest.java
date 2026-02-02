package com.findy.boundedcontext.user.app.usecase;

import com.findy.boundedcontext.user.app.interfaces.UserRepository;
import com.findy.boundedcontext.user.domain.model.User;
import com.findy.boundedcontext.user.domain.model.UserInfo;
import com.findy.boundedcontext.user.domain.model.social.Provider;
import com.findy.boundedcontext.user.domain.model.social.SocialAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetAllUsersUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetAllUsersUseCase getAllUsersUseCase;

    @Test
    @DisplayName("전체 유저 목록 조회 성공")
    void getAllUsersSuccess() {
        UserInfo userInfo1 = new UserInfo("user1", "nick1", "url1");
        SocialAccount socialAccount1 = SocialAccount.create(1L, "user1@example.com", "password", Provider.LOCAL, null);
        User user1 = User.builder()
                .id(1L)
                .userInfo(userInfo1)
                .socialAccount(socialAccount1)
                .build();

        UserInfo userInfo2 = new UserInfo("user2", "nick2", "url2");
        SocialAccount socialAccount2 = SocialAccount.create(2L, "user2@example.com", "password", Provider.LOCAL, null);
        User user2 = User.builder()
                .id(2L)
                .userInfo(userInfo2)
                .socialAccount(socialAccount2)
                .build();

        given(userRepository.findAll(null, 20)).willReturn(List.of(user1, user2));

        List<User> users = getAllUsersUseCase.execute(null, 20);

        assertThat(users).hasSize(2);
        assertThat(users.get(0).getId()).isEqualTo(1L);
        assertThat(users.get(1).getId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("cursor를 이용한 페이징 조회 성공")
    void getAllUsersWithCursorSuccess() {
        UserInfo userInfo = new UserInfo("user3", "nick3", "url3");
        SocialAccount socialAccount = SocialAccount.create(3L, "user3@example.com", "password", Provider.LOCAL, null);
        User user3 = User.builder()
                .id(3L)
                .userInfo(userInfo)
                .socialAccount(socialAccount)
                .build();

        given(userRepository.findAll(2L, 20)).willReturn(List.of(user3));

        List<User> users = getAllUsersUseCase.execute(2L, 20);

        assertThat(users).hasSize(1);
        assertThat(users.get(0).getId()).isEqualTo(3L);
    }
}
