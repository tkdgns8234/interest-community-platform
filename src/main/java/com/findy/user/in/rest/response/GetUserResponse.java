package com.findy.user.in.rest.response;

import com.findy.common.dto.Identifiable;
import com.findy.user.domain.model.social.Provider;

public record GetUserResponse(
    Long id,
    String name,
    String nickname,
    String email,
    String profileImageUrl,
    Provider provider
) implements Identifiable {
}