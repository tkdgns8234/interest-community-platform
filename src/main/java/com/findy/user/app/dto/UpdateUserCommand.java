package com.findy.user.app.dto;

import lombok.Builder;

@Builder
public record UpdateUserCommand(
        long id,
        String name,
        String nickname,
        String profileImageUrl
) {
}
