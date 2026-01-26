package com.findy.user.domain.model;

import com.findy.user.domain.exception.InvalidUserInfoException;
import com.findy.user.domain.exception.SelfFollowException;
import com.findy.user.domain.model.social.SocialAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {

    private UserInfo userInfo;
    private SocialAccount socialAccount;

    @BeforeEach
    void setUp() {
        userInfo = new UserInfo("홍길동", "gildong", "https://example.com/profile.jpg");
        socialAccount = new SocialAccount();
    }

    @Nested
    @DisplayName("User 생성")
    class CreateUser {

        @Test
        @DisplayName("유효한 정보로 User를 생성할 수 있다")
        void createUserWithValidInfo() {
            User user = new User(1L, userInfo, socialAccount);

            assertThat(user.getName()).isEqualTo("홍길동");
            assertThat(user.getNickname()).isEqualTo("gildong");
            assertThat(user.getProfileImageUrl()).isEqualTo("https://example.com/profile.jpg");
        }

        @Test
        @DisplayName("UserInfo가 null이면 예외가 발생한다")
        void throwExceptionWhenUserInfoIsNull() {
            assertThatThrownBy(() -> new User(1L, null, socialAccount))
                    .isInstanceOf(InvalidUserInfoException.class)
                    .hasMessage("UserInfo cannot be null");
        }

        @Test
        @DisplayName("SocialAccount가 null이면 예외가 발생한다")
        void throwExceptionWhenSocialAccountIsNull() {
            assertThatThrownBy(() -> new User(1L, userInfo, null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("SocialAccount cannot be null");
        }
    }

    @Nested
    @DisplayName("팔로우")
    class Follow {

        @Test
        @DisplayName("다른 사용자를 팔로우할 수 있다")
        void followAnotherUser() {
            User user = new User(1L, userInfo, socialAccount);
            User targetUser = new User(2L, new UserInfo("김철수", "cheolsu", "https://example.com/cheolsu.jpg"), new SocialAccount());

            user.follow(targetUser);

            assertThat(user.getFollowManager().getFollowingCount()).isEqualTo(1);
            assertThat(targetUser.getFollowManager().getFollowerCount()).isEqualTo(1);
        }

        @Test
        @DisplayName("자기 자신을 팔로우하면 예외가 발생한다")
        void throwExceptionWhenFollowSelf() {
            User user = new User(1L, userInfo, socialAccount);

            assertThatThrownBy(() -> user.follow(user))
                    .isInstanceOf(SelfFollowException.class)
                    .hasMessage("Cannot follow yourself");
        }

        @Test
        @DisplayName("여러 사용자를 팔로우할 수 있다")
        void followMultipleUsers() {
            User user = new User(1L, userInfo, socialAccount);
            User target1 = new User(2L, new UserInfo("김철수", "cheolsu", "url1"), new SocialAccount());
            User target2 = new User(3L, new UserInfo("이영희", "younghee", "url2"), new SocialAccount());

            user.follow(target1);
            user.follow(target2);

            assertThat(user.getFollowManager().getFollowingCount()).isEqualTo(2);
            assertThat(target1.getFollowManager().getFollowerCount()).isEqualTo(1);
            assertThat(target2.getFollowManager().getFollowerCount()).isEqualTo(1);
        }
    }

    @Nested
    @DisplayName("언팔로우")
    class Unfollow {

        @Test
        @DisplayName("팔로우한 사용자를 언팔로우할 수 있다")
        void unfollowUser() {
            User user = new User(1L, userInfo, socialAccount);
            User targetUser = new User(2L, new UserInfo("김철수", "cheolsu", "url"), new SocialAccount());

            user.follow(targetUser);
            user.unfollow(targetUser);

            assertThat(user.getFollowManager().getFollowingCount()).isEqualTo(0);
            assertThat(targetUser.getFollowManager().getFollowerCount()).isEqualTo(0);
        }

        @Test
        @DisplayName("자기 자신을 언팔로우하면 예외가 발생한다")
        void throwExceptionWhenUnfollowSelf() {
            User user = new User(1L, userInfo, socialAccount);

            assertThatThrownBy(() -> user.unfollow(user))
                    .isInstanceOf(SelfFollowException.class)
                    .hasMessage("Cannot unfollow yourself");
        }

        @Test
        @DisplayName("팔로워 카운트는 0 미만으로 내려가지 않는다")
        void followerCountDoesNotGoBelowZero() {
            User user = new User(1L, userInfo, socialAccount);
            User targetUser = new User(2L, new UserInfo("김철수", "cheolsu", "url"), new SocialAccount());

            user.unfollow(targetUser);

            assertThat(user.getFollowManager().getFollowingCount()).isEqualTo(0);
            assertThat(targetUser.getFollowManager().getFollowerCount()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("동등성")
    class Equality {

        @Test
        @DisplayName("같은 ID를 가진 User는 동등하다")
        void usersWithSameIdAreEqual() {
            User user1 = new User(1L, userInfo, socialAccount);
            User user2 = new User(1L, new UserInfo("다른이름", "other", "url"), new SocialAccount());

            assertThat(user1).isEqualTo(user2);
            assertThat(user1.hashCode()).isEqualTo(user2.hashCode());
        }

        @Test
        @DisplayName("다른 ID를 가진 User는 동등하지 않다")
        void usersWithDifferentIdAreNotEqual() {
            User user1 = new User(1L, userInfo, socialAccount);
            User user2 = new User(2L, userInfo, socialAccount);

            assertThat(user1).isNotEqualTo(user2);
        }
    }
}