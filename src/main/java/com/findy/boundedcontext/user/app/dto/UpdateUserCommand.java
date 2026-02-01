package com.findy.boundedcontext.user.app.dto;

public record UpdateUserCommand(
        long id,
        String name,
        String nickname,
        String profileImageUrl
) {
}
