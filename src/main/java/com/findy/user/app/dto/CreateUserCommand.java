package com.findy.user.app.dto;

import com.findy.user.domain.model.social.Provider;

public record CreateUserCommand(
        Provider provider,
        String email,
        String password,
        String name,
        String nickname,
        String profileImageUrl
) {
}
