package com.findy.user.in.rest;

import com.findy.common.dto.CursorPageResponse;
import com.findy.common.dto.IdResponse;
import com.findy.user.app.UserService;
import com.findy.user.domain.model.User;
import com.findy.user.in.rest.mapper.UserRestMapper;
import com.findy.user.in.rest.request.CreateUserRequest;
import com.findy.user.in.rest.request.UpdateUserRequest;
import com.findy.user.in.rest.response.GetUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name ="회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class ApiV1UserController {
    private final UserService userService;
    private final UserRestMapper mapper;

    @Operation(summary = "회원 조회")
    @GetMapping("/{userId}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        val response = mapper.toGetUserResponse(user);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "회원 생성")
    @PostMapping
    public ResponseEntity<IdResponse> createUser(@RequestBody CreateUserRequest request) {
        val command = mapper.toCreateCommand(request);
        User user = userService.createUser(command);
        return ResponseEntity.ok(new IdResponse(user.getId()));
    }

    @Operation(summary = "회원 정보 수정")
    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateUserInfo(
            @PathVariable Long userId,
            @RequestBody UpdateUserRequest request) {
        val command = mapper.toUpdateCommand(userId, request);
        userService.updateUserInfo(command);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "전체 회원 목록 조회")
    @GetMapping
    public ResponseEntity<CursorPageResponse<GetUserResponse>> getAllUsers(
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "20") int size) {
        List<User> users = userService.getAllUsers(cursor, size);
        val response = mapper.toGetUserPageResponse(users, size);
        return ResponseEntity.ok(response);
    }
}
