package com.findy.user.in.rest.request;

import com.findy.user.domain.model.social.Provider;

public record CreateUserRequestDTO(
        Provider provider,
        String email,
        String password,
        String name,
        String nickname,
        String profileImageUrl
) {
}
