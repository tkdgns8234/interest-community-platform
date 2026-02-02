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
class GetFollowingsUseCaseTest {

    @Mock
    private UserRelationRepository userRelationRepository;

    @InjectMocks
    private GetFollowingsUseCase getFollowingsUseCase;

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
    @DisplayName("팔로잉 목록 조회 성공")
    void getFollowingsSuccess() {
        List<User> followings = List.of(
                createUser(3L, "following1", "following1@example.com"),
                createUser(4L, "following2", "following2@example.com")
        );
        given(userRelationRepository.findFollowings(1L, null, 20)).willReturn(followings);

        List<User> result = getFollowingsUseCase.execute(1L, null, 20);

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getNickname()).isEqualTo("following1");
        assertThat(result.get(1).getNickname()).isEqualTo("following2");
    }

    @Test
    @DisplayName("cursor를 이용한 페이징 조회 성공")
    void getFollowingsWithCursor() {
        List<User> followings = List.of(
                createUser(5L, "following3", "following3@example.com")
        );
        given(userRelationRepository.findFollowings(1L, 3L, 20)).willReturn(followings);

        List<User> result = getFollowingsUseCase.execute(1L, 3L, 20);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNickname()).isEqualTo("following3");
    }
}
