package com.findy.user.app.dto;

public record UpdateUserCommand(
        long id,
        String name,
        String nickname,
        String profileImageUrl
) {
}
