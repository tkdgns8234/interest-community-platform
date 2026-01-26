package com.findy.user.in.rest.response;

import com.findy.common.dto.Identifiable;

public record GetFollowingsResponse(
        Long id,
        String nickname,
        String profileImageUrl
) implements Identifiable {
}
