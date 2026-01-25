package com.findy.user.in.rest.response;

import lombok.Builder;

@Builder
public record CreateUserResponse(
    Long id,
    String name,
    String nickname,
    String email,
    String profileImageUrl
) {
}
