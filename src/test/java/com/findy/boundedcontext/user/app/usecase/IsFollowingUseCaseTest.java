package com.findy.boundedcontext.user.app.usecase;

import com.findy.boundedcontext.user.app.interfaces.UserRelationRepository;
import com.findy.boundedcontext.user.app.interfaces.UserRepository;
import com.findy.boundedcontext.user.domain.model.FollowManager;
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
class IsFollowingUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRelationRepository userRelationRepository;

    @InjectMocks
    private IsFollowingUseCase isFollowingUseCase;

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
    @DisplayName("팔로우 중이면 true 반환")
    void isFollowingTrue() {
        given(userRepository.findById(1L)).willReturn(user);
        given(userRepository.findById(2L)).willReturn(targetUser);
        given(userRelationRepository.isFollowing(user, targetUser)).willReturn(true);

        boolean result = isFollowingUseCase.execute(1L, 2L);

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("팔로우 중이 아니면 false 반환")
    void isFollowingFalse() {
        given(userRepository.findById(1L)).willReturn(user);
        given(userRepository.findById(2L)).willReturn(targetUser);
        given(userRelationRepository.isFollowing(user, targetUser)).willReturn(false);

        boolean result = isFollowingUseCase.execute(1L, 2L);

        assertThat(result).isFalse();
    }
}
