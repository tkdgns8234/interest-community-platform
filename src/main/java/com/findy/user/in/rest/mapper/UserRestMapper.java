package com.findy.user.in.rest.mapper;

import com.findy.user.app.dto.CreateUserCommand;
import com.findy.user.app.dto.UpdateUserCommand;
import com.findy.user.domain.model.User;
import com.findy.user.in.rest.request.CreateUserRequest;
import com.findy.user.in.rest.request.UpdateUserRequest;
import com.findy.user.in.rest.response.CreateUserResponse;
import com.findy.user.in.rest.response.GetUserResponse;
import com.findy.user.in.rest.response.UpdateUserResponse;

public class UserRestMapper {
    public static CreateUserCommand toCommand(CreateUserRequest req) {
        return CreateUserCommand.builder().
                provider(req.provider()).
                email(req.email()).
                password(req.password()).
                name(req.name()).
                nickname(req.nickname()).
                profileImageUrl(req.profileImageUrl()).
                build();
    }

    public static CreateUserResponse toCreateResponse(User user) {
        return CreateUserResponse.builder()
                .id(user.getId())
                .name(user.getUserInfo().getName())
                .nickname(user.getUserInfo().getNickname())
                .email(user.getSocialAccount().getEmail())
                .profileImageUrl(user.getUserInfo().getProfileImageUrl())
                .build();
    }

    public static GetUserResponse toGetResponse(User user) {
        return GetUserResponse.builder()
                .id(user.getId())
                .name(user.getUserInfo().getName())
                .nickname(user.getUserInfo().getNickname())
                .email(user.getSocialAccount().getEmail())
                .profileImageUrl(user.getUserInfo().getProfileImageUrl())
                .provider(user.getSocialAccount().getProvider())
                .build();
    }

    public static UpdateUserResponse toUpdateResponse(User user) {
        return UpdateUserResponse.builder()
                .id(user.getId())
                .name(user.getUserInfo().getName())
                .nickname(user.getUserInfo().getNickname())
                .profileImageUrl(user.getUserInfo().getProfileImageUrl())
                .build();
    }

    public static UpdateUserCommand toUpdateCommand(UpdateUserRequest req) {
        return UpdateUserCommand.builder()
                .id(req.id())
                .name(req.name())
                .nickname(req.nickname())
                .profileImageUrl(req.profileImageUrl())
                .build();
    }
}
