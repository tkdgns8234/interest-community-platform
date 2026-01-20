package com.findy.user.application.dto;

import com.findy.user.domain.User;

public record GetUserResponseDTO(
    Long id,
    String name,
    String nickname,
    String email,
    String profileImageUrl
) {
    public GetUserResponseDTO(User user) {
        this(
            user.getId(),
            user.getName(),
            user.getNickname(),
            user.getEmail(),
            user.getProfileImageUrl()
        );
    }
}