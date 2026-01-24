package com.findy.user.presentation.web.user.response;

import com.findy.user.domain.model.User;

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
