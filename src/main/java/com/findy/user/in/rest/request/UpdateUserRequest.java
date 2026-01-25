package com.findy.user.in.rest.request;

public record UpdateUserRequest(
    String name,
    String nickname,
    String profileImageUrl
) {
}
