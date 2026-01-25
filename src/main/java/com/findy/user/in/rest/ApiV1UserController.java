package com.findy.user.in.rest;

import com.findy.user.app.UserService;
import com.findy.user.domain.model.User;
import com.findy.user.in.rest.mapper.UserRestMapper;
import com.findy.user.in.rest.request.CreateUserRequest;
import com.findy.user.in.rest.request.UpdateUserRequest;
import com.findy.user.in.rest.response.CreateUserResponse;
import com.findy.user.in.rest.response.GetUserResponse;
import com.findy.user.in.rest.response.UpdateUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name ="회원 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class ApiV1UserController {
    private final UserService userService;

    @Operation(summary = "회원 조회")
    @GetMapping("/{userId}")
    public GetUserResponse getUser(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        return UserRestMapper.toGetResponse(user);
    }

    @Operation(summary = "회원 생성")
    @PostMapping
    public CreateUserResponse createUser(@RequestBody CreateUserRequest createUserRequestDTO) {
        val command = UserRestMapper.toCommand(createUserRequestDTO);
        User user = userService.createUser(command);
        return UserRestMapper.toCreateResponse(user);
    }

    @Operation(summary = "회원 정보 수정")
    @PatchMapping
    public UpdateUserResponse updateUserInfo(@RequestBody UpdateUserRequest updateUserRequestDTO) {
        val command = UserRestMapper.toUpdateCommand(updateUserRequestDTO);
        User user = userService.updateUserInfo(command);
        return UserRestMapper.toUpdateResponse(user);
    }
}
