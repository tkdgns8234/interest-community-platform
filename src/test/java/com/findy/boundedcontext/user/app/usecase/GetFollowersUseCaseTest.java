package com.findy.boundedcontext.user.app.usecase;

import com.findy.boundedcontext.user.app.interfaces.UserRelationRepository;
import com.findy.boundedcontext.user.domain.model.FollowManager;
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
class GetFollowersUseCaseTest {

    @Mock
    private UserRelationRepository userRelationRepository;

    @InjectMocks
    private GetFollowersUseCase getFollowersUseCase;

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
    @DisplayName("팔로워 목록 조회 성공")
    void getFollowersSuccess() {
        List<User> followers = List.of(
                createUser(3L, "follower1", "follower1@example.com"),
                createUser(4L, "follower2", "follower2@example.com")
        );
        given(userRelationRepository.findFollowers(1L, null, 20)).willReturn(followers);

        List<User> result = getFollowersUseCase.execute(1L, null, 20);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getNickname()).isEqualTo("follower1");
        assertThat(result.get(1).getNickname()).isEqualTo("follower2");
    }

    @Test
    @DisplayName("cursor를 이용한 페이징 조회 성공")
    void getFollowersWithCursor() {
        List<User> followers = List.of(
                createUser(5L, "follower3", "follower3@example.com")
        );
        given(userRelationRepository.findFollowers(1L, 3L, 20)).willReturn(followers);

        List<User> result = getFollowersUseCase.execute(1L, 3L, 20);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNickname()).isEqualTo("follower3");
    }
}
