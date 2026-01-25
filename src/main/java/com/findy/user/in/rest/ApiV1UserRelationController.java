package com.findy.user.in.rest;

import com.findy.user.app.UserRelationService;
import com.findy.user.in.rest.request.FollowUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원 관계 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/relation")
public class ApiV1UserRelationController {
    private final UserRelationService userRelationService;

    //TODO:: followers, followings paging 조회

    @Operation(summary = "팔로우")
    @PostMapping("/follow")
    public ResponseEntity<Void> followUser(@RequestBody FollowUserRequest dto) {
        userRelationService.follow(dto.userId(), dto.targetUserId());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "언팔로우")
    @PostMapping("/unfollow")
    public ResponseEntity<Void> unfollowUser(@RequestBody FollowUserRequest dto) {
        userRelationService.unfollow(dto.userId(), dto.targetUserId());
        return ResponseEntity.ok().build();
    }
}
