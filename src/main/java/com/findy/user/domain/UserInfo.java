package com.findy.user.domain;

public class UserInfo {
    private final String name;
    private final String nickname;
    private final String profileImageUrl;

    public UserInfo(String name, String nickname, String profileImageUrl) {
        this.name = name;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
