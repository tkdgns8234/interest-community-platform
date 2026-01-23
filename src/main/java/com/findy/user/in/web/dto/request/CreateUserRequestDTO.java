package com.findy.user.in.web.dto.request;

import com.findy.user.domain.social.Provider;

public record CreateUserRequestDTO(
        Provider provider,
        String email,
        String password,
        String name,
        String nickname,
        String profileImageUrl
) {
}
