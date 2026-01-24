package com.findy.user.in.rest.response;

import com.findy.user.domain.model.User;
import com.findy.user.domain.model.social.Provider;

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