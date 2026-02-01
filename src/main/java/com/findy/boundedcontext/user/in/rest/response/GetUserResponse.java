package com.findy.boundedcontext.user.in.rest.response;

import com.findy.global.dto.Identifiable;
import com.findy.boundedcontext.user.domain.model.social.Provider;

public record GetUserResponse(
    Long id,
    String name,
    String nickname,
    String email,
    String profileImageUrl,
    Provider provider
) implements Identifiable {
}