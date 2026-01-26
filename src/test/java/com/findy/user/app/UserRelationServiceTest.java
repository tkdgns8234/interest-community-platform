package com.findy.user.app;

import com.findy.user.app.exception.UserAlreadyFollowException;
import com.findy.user.app.exception.UserNotFollowedException;
import com.findy.user.app.interfaces.UserRelationRepository;
import com.findy.user.domain.model.FollowManager;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserRelationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRelationRepository userRelationRepository;

    @InjectMocks
    private UserRelationService userRelationService;

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

    @Nested
    @DisplayName("팔로우")
    class Follow {

        @Test
        @DisplayName("다른 유저를 팔로우할 수 있다")
        void followSuccess() {
            given(userService.getUser(1L)).willReturn(user);
            given(userService.getUser(2L)).willReturn(targetUser);
            given(userRelationRepository.isFollowing(user, targetUser)).willReturn(false);

            userRelationService.follow(1L, 2L);

            verify(userRelationRepository).follow(any(User.class), any(User.class));
        }

        @Test
        @DisplayName("이미 팔로우한 유저를 다시 팔로우하면 예외가 발생한다")
        void followAlreadyFollowing() {
            given(userService.getUser(1L)).willReturn(user);
            given(userService.getUser(2L)).willReturn(targetUser);
            given(userRelationRepository.isFollowing(user, targetUser)).willReturn(true);

            assertThatThrownBy(() -> userRelationService.follow(1L, 2L))
                    .isInstanceOf(UserAlreadyFollowException.class);
        }
    }

    @Nested
    @DisplayName("언팔로우")
    class Unfollow {

        @Test
        @DisplayName("팔로우한 유저를 언팔로우할 수 있다")
        void unfollowSuccess() {
            given(userService.getUser(1L)).willReturn(user);
            given(userService.getUser(2L)).willReturn(targetUser);
            given(userRelationRepository.isFollowing(user, targetUser)).willReturn(true);

            userRelationService.unfollow(1L, 2L);

            verify(userRelationRepository).unfollow(any(User.class), any(User.class));
        }

        @Test
        @DisplayName("팔로우하지 않은 유저를 언팔로우하면 예외가 발생한다")
        void unfollowNotFollowing() {
            given(userService.getUser(1L)).willReturn(user);
            given(userService.getUser(2L)).willReturn(targetUser);
            given(userRelationRepository.isFollowing(user, targetUser)).willReturn(false);

            assertThatThrownBy(() -> userRelationService.unfollow(1L, 2L))
                    .isInstanceOf(UserNotFollowedException.class);
        }
    }

    @Nested
    @DisplayName("팔로우 여부 확인")
    class IsFollowing {

        @Test
        @DisplayName("팔로우 중이면 true를 반환한다")
        void isFollowingTrue() {
            given(userService.getUser(1L)).willReturn(user);
            given(userService.getUser(2L)).willReturn(targetUser);
            given(userRelationRepository.isFollowing(user, targetUser)).willReturn(true);

            boolean result = userRelationService.isFollowing(1L, 2L);

            assertThat(result).isTrue();
        }

        @Test
        @DisplayName("팔로우 중이 아니면 false를 반환한다")
        void isFollowingFalse() {
            given(userService.getUser(1L)).willReturn(user);
            given(userService.getUser(2L)).willReturn(targetUser);
            given(userRelationRepository.isFollowing(user, targetUser)).willReturn(false);

            boolean result = userRelationService.isFollowing(1L, 2L);

            assertThat(result).isFalse();
        }
    }

    @Nested
    @DisplayName("팔로워 목록 조회")
    class GetFollowers {

        @Test
        @DisplayName("팔로워 목록을 조회할 수 있다")
        void getFollowersSuccess() {
            List<User> followers = List.of(
                    createUser(3L, "follower1", "follower1@example.com"),
                    createUser(4L, "follower2", "follower2@example.com")
            );
            given(userRelationRepository.findFollowers(1L, null, 20)).willReturn(followers);

            List<User> result = userRelationService.getFollowers(1L, null, 20);

            assertThat(result).hasSize(2);
            assertThat(result.get(0).getNickname()).isEqualTo("follower1");
            assertThat(result.get(1).getNickname()).isEqualTo("follower2");
        }

        @Test
        @DisplayName("cursor를 이용해 다음 페이지를 조회할 수 있다")
        void getFollowersWithCursor() {
            List<User> followers = List.of(
                    createUser(5L, "follower3", "follower3@example.com")
            );
            given(userRelationRepository.findFollowers(1L, 3L, 20)).willReturn(followers);

            List<User> result = userRelationService.getFollowers(1L, 3L, 20);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getNickname()).isEqualTo("follower3");
        }
    }

    @Nested
    @DisplayName("팔로잉 목록 조회")
    class GetFollowings {

        @Test
        @DisplayName("팔로잉 목록을 조회할 수 있다")
        void getFollowingsSuccess() {
            List<User> followings = List.of(
                    createUser(3L, "following1", "following1@example.com"),
                    createUser(4L, "following2", "following2@example.com")
            );
            given(userRelationRepository.findFollowings(1L, null, 20)).willReturn(followings);

            List<User> result = userRelationService.getFollowings(1L, null, 20);

            assertThat(result).hasSize(2);
            assertThat(result.get(0).getNickname()).isEqualTo("following1");
            assertThat(result.get(1).getNickname()).isEqualTo("following2");
        }
    }
}