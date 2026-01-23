package com.findy.user.in.web.dto.response;

import com.findy.user.domain.User;
import com.findy.user.domain.social.Provider;

public record GetUserResponseDTO(
    Long id,
    String name,
    String nickname,
    String email,
    String profileImageUrl,
    Provider provider
) {
    public GetUserResponseDTO(User user) {
        this(
            user.getId(),
            user.getName(),
            user.getNickname(),
            user.getEmail(),
            user.getProfileImageUrl(),
            user.getProvider()
        );
    }
}