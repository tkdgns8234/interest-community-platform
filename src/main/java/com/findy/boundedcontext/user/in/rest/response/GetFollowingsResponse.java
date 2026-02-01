package com.findy.boundedcontext.user.in.rest.response;

import com.findy.global.dto.Identifiable;

public record GetFollowingsResponse(
        Long id,
        String nickname,
        String profileImageUrl
) implements Identifiable {
}
