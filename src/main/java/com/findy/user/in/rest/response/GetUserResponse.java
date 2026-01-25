package com.findy.user.in.rest.response;

import com.findy.user.domain.model.social.Provider;
import lombok.Builder;

@Builder
public record GetUserResponse(
    Long id,
    String name,
    String nickname,
    String email,
    String profileImageUrl,
    Provider provider
) {
}