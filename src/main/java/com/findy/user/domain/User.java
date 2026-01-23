package com.findy.user.domain;

import com.findy.user.domain.followmanager.FollowManager;
import com.findy.user.domain.social.Provider;
import com.findy.user.domain.social.SocialAccount;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class User {
    Long id;
    UserInfo userInfo;
    SocialAccount socialAccount;
    FollowManager followManager;

    public User (Long id, UserInfo userInfo, SocialAccount socialAccount) {
        this(id, userInfo, socialAccount, new FollowManager());
    }

    @Builder
    public User (Long id, UserInfo userInfo, SocialAccount socialAccount, FollowManager followManager) {
        if (userInfo == null) {
            throw new IllegalArgumentException("UserInfo cannot be null");
        }

        this.id = id;
        this.userInfo = userInfo;
        this.socialAccount = socialAccount;
        this.followManager = followManager;
    }

    public void updateUserInfo(String nickname, String profileImageUrl) {
        userInfo.update(nickname, profileImageUrl);
    }

    public void follow(User targetUser) {
        if (this.equals(targetUser)) {
            throw new IllegalArgumentException("Cannot follow yourself");
        }

        followManager.increaseFollowingCount();
        targetUser.followManager.increaseFollowerCount(); // 객체지향 생활체조원칙 디미터법칙 위반이지만, 향후 follow 작업 관련 기능 확장(알림 등)이 있는 경우 followManager 에서 처리 가능함 (복잡성 줄이기위한 일부 trade-off)
    }

    public void unfollow(User targetUser) {
        if (this.equals(targetUser)) {
            throw new IllegalArgumentException("Cannot unfollow yourself");
        }

        followManager.decreaseFollowingCount();
        targetUser.followManager.decreaseFollowerCount();
    }

    public String getName() {
        return userInfo.getName();
    }

    public String getNickname() {
        return userInfo.getNickname();
    }

    public String getProfileImageUrl() {
        return userInfo.getProfileImageUrl();
    }

    public String getEmail() {
        return socialAccount.getEmail();
    }

    public Provider getProvider() {
        return socialAccount.getProvider();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
