package com.findy.boundedcontext.user.in.rest.request;

import com.findy.boundedcontext.user.domain.model.social.Provider;

public record CreateUserRequest(
        Provider provider,
        String email,
        String password,
        String name,
        String nickname,
        String profileImageUrl
) {
}
