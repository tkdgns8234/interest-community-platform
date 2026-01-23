package com.findy.user.in.web.dto.response;

import com.findy.user.domain.User;

public record CreateUserResponseDTO(
    Long id,
    String name,
    String nickname,
    String email,
    String profileImageUrl
) {
    public CreateUserResponseDTO(User user) {
        this(
            user.getId(),
            user.getName(),
            user.getNickname(),
            user.getEmail(),
            user.getProfileImageUrl()
        );
    }
}
