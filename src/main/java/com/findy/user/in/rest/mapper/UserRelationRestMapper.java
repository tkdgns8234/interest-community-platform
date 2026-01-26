package com.findy.user.in.rest.mapper;

import com.findy.common.dto.CursorPageResponse;
import com.findy.user.domain.model.User;
import com.findy.user.in.rest.response.GetFollowersResponse;
import com.findy.user.in.rest.response.GetFollowingsResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRelationRestMapper {
    public CursorPageResponse<GetFollowersResponse> toFollowerPageResponse(List<User> users, int size) {
        List<GetFollowersResponse> responses = users.stream()
                .map(user -> new GetFollowersResponse(
                        user.getId(),
                        user.getNickname(),
                        user.getProfileImageUrl()
                ))
                .toList();

        return CursorPageResponse.of(responses, size);
    }

    public CursorPageResponse<GetFollowingsResponse> toFollowingPageResponse(List<User> users, int size) {
        List<GetFollowingsResponse> responses = users.stream()
                .map(user -> new GetFollowingsResponse(
                        user.getId(),
                        user.getNickname(),
                        user.getProfileImageUrl()
                ))
                .toList();

        return CursorPageResponse.of(responses, size);
    }
}
