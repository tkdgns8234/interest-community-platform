package com.findy.user.presentation.api.user.request;

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
