package com.findy.user.presentation.web.user;

import com.findy.user.application.user.UserService;
import com.findy.user.domain.model.User;
import com.findy.user.presentation.web.user.request.CreateUserRequestDTO;
import com.findy.user.presentation.web.user.request.UpdateUserRequestDTO;
import com.findy.user.presentation.web.user.response.CreateUserResponseDTO;
import com.findy.user.presentation.web.user.response.GetUserResponseDTO;
import com.findy.user.presentation.web.user.response.UpdateUserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    public GetUserResponseDTO getUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return new GetUserResponseDTO(user);
    }

    @Operation(summary = "회원 생성")
    @PostMapping
    public CreateUserResponseDTO createUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
        User user = userService.createUser(createUserRequestDTO);
        return new CreateUserResponseDTO(user);
    }

    @Operation(summary = "회원 정보 수정")
    @PatchMapping
    public UpdateUserResponseDto updateUserInfo(@RequestBody UpdateUserRequestDTO updateUserRequestDTO) {
        User user = userService.updateUserInfo(updateUserRequestDTO);
        return new UpdateUserResponseDto(user);
    }
}
