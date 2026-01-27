package com.findy.user.in.rest.mapper;

import com.findy.common.dto.CursorPageResponse;
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
        return new CreateUserCommand(
                req.provider(),
                req.email(),
                req.password(),
                req.name(),
                req.nickname(),
                req.profileImageUrl()
        );
    }

    public GetUserResponse toGetUserResponse(User user) {
        return new GetUserResponse(
                user.getId(),
                user.getName(),
                user.getNickname(),
                user.getEmail(),
                user.getProfileImageUrl(),
                user.getProvider()
        );
    }

    public UpdateUserCommand toUpdateCommand(Long userId, UpdateUserRequest req) {
        return new UpdateUserCommand(
            userId,
            req.name(),
            req.nickname(),
            req.profileImageUrl()
        );
    }

    public CursorPageResponse<GetUserResponse> toGetUserPageResponse(List<User> users, int size) {
        List<GetUserResponse> responses = users.stream()
                .map(this::toGetUserResponse)
                .toList();

        return CursorPageResponse.of(responses, size);
    }
}
