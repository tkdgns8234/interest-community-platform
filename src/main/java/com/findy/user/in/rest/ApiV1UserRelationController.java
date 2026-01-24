package com.findy.user.in.rest;

import com.findy.user.app.UserRelationService;
import com.findy.user.in.rest.request.FollowUserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/relation")
public class ApiV1UserRelationController {
    private final UserRelationService userRelationService;

    //TODO:: followers, followings paging 조회
    //TODO:: following count, follower count 조회

    //TODO:: follow, unfollow 구현
    @PostMapping("/follow")
    public void followUser(@RequestBody FollowUserRequestDTO dto) {
        userRelationService.follow(dto.userId(), dto.targetUserId());
    }
}
