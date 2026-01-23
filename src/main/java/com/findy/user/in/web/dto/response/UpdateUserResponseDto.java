package com.findy.user.in.web.dto.response;

import com.findy.user.domain.User;

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
