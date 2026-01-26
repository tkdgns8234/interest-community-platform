package com.findy.user.in.rest.mapper;

import com.findy.user.app.dto.CreateUserCommand;
import com.findy.user.app.dto.UpdateUserCommand;
import com.findy.user.domain.model.User;
import com.findy.user.in.rest.request.CreateUserRequest;
import com.findy.user.in.rest.request.UpdateUserRequest;
import com.findy.user.in.rest.response.GetUserResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRestMapper {

    public CreateUserCommand toCreateCommand(CreateUserRequest req) {
        return CreateUserCommand.builder()
                .provider(req.provider())
                .email(req.email())
                .password(req.password())
                .name(req.name())
                .nickname(req.nickname())
                .profileImageUrl(req.profileImageUrl())
                .build();
    }

    public GetUserResponse toGetResponse(User user) {
        return GetUserResponse.builder()
                .id(user.getId())
                .name(user.getUserInfo().getName())
                .nickname(user.getUserInfo().getNickname())
                .email(user.getSocialAccount().getEmail())
                .profileImageUrl(user.getUserInfo().getProfileImageUrl())
                .provider(user.getSocialAccount().getProvider())
                .build();
    }

    public UpdateUserCommand toUpdateCommand(Long userId, UpdateUserRequest req) {
        return UpdateUserCommand.builder()
                .id(userId)
                .name(req.name())
                .nickname(req.nickname())
                .profileImageUrl(req.profileImageUrl())
                .build();
    }
}
