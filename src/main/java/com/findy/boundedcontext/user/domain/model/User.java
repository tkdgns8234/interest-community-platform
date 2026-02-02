package com.findy.boundedcontext.user.domain.model;

import com.findy.boundedcontext.user.domain.exception.InvalidUserInfoException;
import com.findy.boundedcontext.user.domain.exception.SelfFollowException;
import com.findy.boundedcontext.user.domain.model.social.Provider;
import com.findy.boundedcontext.user.domain.model.social.SocialAccount;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class User {
    private final Long id;
    private UserInfo userInfo;
    private final SocialAccount socialAccount;
    private final FollowManager followManager;

    public User (Long id, UserInfo userInfo, SocialAccount socialAccount) {
        this(id, userInfo, socialAccount, new FollowManager());
    }

    @Builder
    public User (Long id, UserInfo userInfo, SocialAccount socialAccount, FollowManager followManager) {
        if (userInfo == null) {
            throw new InvalidUserInfoException("UserInfo cannot be null");
        }
        if (socialAccount == null) {
            throw new IllegalArgumentException("SocialAccount cannot be null");
        }

        this.id = id;
        this.userInfo = userInfo;
        this.socialAccount = socialAccount;
        this.followManager = followManager;
    }

    public void updateUserInfo(String nickname, String profileImageUrl) {
        this.userInfo = userInfo.withNicknameAndProfileImageUrl(nickname, profileImageUrl);
    }

    public void follow(User targetUser) {
        if (this.equals(targetUser)) {
            throw new SelfFollowException("Cannot follow yourself");
        }

        followManager.increaseFollowingCount();
        targetUser.followManager.increaseFollowerCount();
    }

    public void unfollow(User targetUser) {
        if (this.equals(targetUser)) {
            throw new SelfFollowException("Cannot unfollow yourself");
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

    public FollowManager getFollowManager() {
        return followManager;
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
