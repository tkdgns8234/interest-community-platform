package com.findy.user.domain;

public class UserInfo {
    private final String name;
    private String nickname;
    private String profileImageUrl;

    public UserInfo(String name, String nickname, String profileImageUrl) {
        // TODO: 이름, 닉네임 유효성 검사 추가
        this.name = name;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public void update(String nickname, String profileImageUrl) {
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
