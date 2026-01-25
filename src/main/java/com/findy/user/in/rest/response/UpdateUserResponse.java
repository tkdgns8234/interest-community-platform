package com.findy.user.in.rest.response;

import lombok.Builder;

@Builder
public record UpdateUserResponse(
    long id,
    String name,
    String nickname,
    String profileImageUrl
) {
}
