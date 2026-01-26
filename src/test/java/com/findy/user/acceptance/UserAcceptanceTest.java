package com.findy.user.acceptance;

import tools.jackson.databind.ObjectMapper;
import com.findy.user.domain.model.social.Provider;
import com.findy.user.in.rest.request.CreateUserRequest;
import com.findy.user.in.rest.request.UpdateUserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("회원을 생성할 수 있다")
    void createUser() throws Exception {
        CreateUserRequest request = new CreateUserRequest(
                Provider.LOCAL,
                "test@example.com",
                "password123",
                "홍길동",
                "gildong",
                "https://example.com/profile.jpg"
        );

        mockMvc.perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Test
    @DisplayName("회원을 조회할 수 있다")
    void getUser() throws Exception {
        // given: 회원 생성
        Long userId = createTestUser("test@example.com", "gildong");

        // when & then
        mockMvc.perform(get("/api/v1/user/{userId}", userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.nickname").value("gildong"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    @DisplayName("회원 정보를 수정할 수 있다")
    void updateUser() throws Exception {
        // given: 회원 생성
        Long userId = createTestUser("test@example.com", "gildong");

        UpdateUserRequest request = new UpdateUserRequest(
                "홍길동 수정",
                "gildong_updated",
                "https://example.com/new_profile.jpg"
        );

        // when & then: 수정
        mockMvc.perform(patch("/api/v1/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNoContent());

        // then: 수정된 내용 확인
        mockMvc.perform(get("/api/v1/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname").value("gildong_updated"));
    }

    private Long createTestUser(String email, String nickname) throws Exception {
        CreateUserRequest request = new CreateUserRequest(
                Provider.LOCAL,
                email,
                "password123",
                "홍길동",
                nickname,
                "https://example.com/profile.jpg"
        );

        String response = mockMvc.perform(post("/api/v1/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(response).get("id").asLong();
    }
}