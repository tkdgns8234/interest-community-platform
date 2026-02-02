package com.findy.boundedcontext.user.app.usecase;

import com.findy.boundedcontext.user.app.exception.UserAlreadyFollowException;
import com.findy.boundedcontext.user.app.interfaces.UserRelationRepository;
import com.findy.boundedcontext.user.app.interfaces.UserRepository;
import com.findy.boundedcontext.user.domain.model.FollowManager;
import com.findy.boundedcontext.user.domain.model.User;
import com.findy.boundedcontext.user.domain.model.UserInfo;
import com.findy.boundedcontext.user.domain.model.social.Provider;
import com.findy.boundedcontext.user.domain.model.social.SocialAccount;
import com.findy.boundedcontext.user.domain.service.UserRelationPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FollowUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRelationRepository userRelationRepository;

    @Mock
    private UserRelationPolicy userRelationPolicy;

    @InjectMocks
    private FollowUserUseCase followUserUseCase;

    private User user;
    private User targetUser;

    @BeforeEach
    void setUp() {
        user = createUser(1L, "user1", "user1@example.com");
        targetUser = createUser(2L, "user2", "user2@example.com");
    }

    private User createUser(Long id, String nickname, String email) {
        UserInfo userInfo = new UserInfo("name", nickname, "https://example.com/profile.jpg");
        SocialAccount socialAccount = SocialAccount.create(id, email, "password", Provider.LOCAL, null);
        return User.builder()
                .id(id)
                .userInfo(userInfo)
                .socialAccount(socialAccount)
                .followManager(new FollowManager())
                .build();
    }

    @Test
    @DisplayName("팔로우 성공")
    void followSuccess() {
        given(userRepository.findById(1L)).willReturn(user);
        given(userRepository.findById(2L)).willReturn(targetUser);

        followUserUseCase.execute(1L, 2L);

        verify(userRelationPolicy).validateFollow(user, targetUser);
        verify(userRelationRepository).follow(any(User.class), any(User.class));
    }

    @Test
    @DisplayName("이미 팔로우한 유저를 다시 팔로우하면 예외 발생")
    void followAlreadyFollowing() {
        given(userRepository.findById(1L)).willReturn(user);
        given(userRepository.findById(2L)).willReturn(targetUser);
        doThrow(new UserAlreadyFollowException())
                .when(userRelationPolicy).validateFollow(user, targetUser);

        assertThatThrownBy(() -> followUserUseCase.execute(1L, 2L))
                .isInstanceOf(UserAlreadyFollowException.class);
    }
}
