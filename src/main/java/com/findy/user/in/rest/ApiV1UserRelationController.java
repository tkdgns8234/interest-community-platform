package com.findy.user.in.rest;

import com.findy.common.dto.CursorPageResponse;
import com.findy.user.app.UserRelationService;
import com.findy.user.domain.model.User;
import com.findy.user.in.rest.mapper.UserRelationRestMapper;
import com.findy.user.in.rest.request.FollowUserRequest;
import com.findy.user.in.rest.response.GetFollowersResponse;
import com.findy.user.in.rest.response.GetFollowingsResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "회원 관계 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user/relation")
public class ApiV1UserRelationController {
    private final UserRelationService userRelationService;
    private final UserRelationRestMapper mapper;

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

    @Operation(summary = "팔로워 목록 조회")
    @GetMapping("/{userId}/followers")
    public ResponseEntity<CursorPageResponse<GetFollowersResponse>> getFollowers(
            @PathVariable Long userId,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "20") int size) {
        List<User> users = userRelationService.getFollowers(userId, cursor, size);
        val response = mapper.toFollowerPageResponse(users, size);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "팔로우 목록 조회")
    @GetMapping("/{userId}/followings")
    public ResponseEntity<CursorPageResponse<GetFollowingsResponse>> getFollowings(
            @PathVariable Long userId,
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "20") int size) {
        List<User> users = userRelationService.getFollowings(userId, cursor, size);
        val response = mapper.toFollowingPageResponse(users, size);
        return ResponseEntity.ok(response);
    }

}
