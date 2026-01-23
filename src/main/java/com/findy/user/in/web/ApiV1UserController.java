package com.findy.user.in.web;

import com.findy.user.app.UserService;
import com.findy.user.domain.User;
import com.findy.user.in.web.dto.request.CreateUserRequestDTO;
import com.findy.user.in.web.dto.response.CreateUserResponseDTO;
import com.findy.user.in.web.dto.response.GetUserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class ApiV1UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public GetUserResponseDTO getUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return new GetUserResponseDTO(user);
    }

    @PostMapping
    public CreateUserResponseDTO createUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
        User user = userService.createUser(createUserRequestDTO);
        return new CreateUserResponseDTO(user);
    }
}
