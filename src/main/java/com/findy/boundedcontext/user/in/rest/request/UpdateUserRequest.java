package com.findy.boundedcontext.user.in.rest.request;

public record UpdateUserRequest(
    String name,
    String nickname,
    String profileImageUrl
) {
}
