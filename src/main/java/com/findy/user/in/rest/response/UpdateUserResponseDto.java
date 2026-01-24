package com.findy.user.in.rest.response;

import com.findy.user.domain.model.User;

public record UpdateUserResponseDto(
    long id,
    String name,
    String nickname,
    String profileImageUrl
) {
    public UpdateUserResponseDto(User user) {
        this(
            user.getId(),
            user.getUserInfo().getName(),
            user.getUserInfo().getNickname(),
            user.getUserInfo().getProfileImageUrl()
        );
    }
}
