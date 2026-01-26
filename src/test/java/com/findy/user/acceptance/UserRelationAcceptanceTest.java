package com.findy.user.acceptance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.findy.user.domain.model.social.Provider;
import com.findy.user.in.rest.request.CreateUserRequest;
import com.findy.user.in.rest.request.FollowUserRequest;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserRelationAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long user1Id;
    private Long user2Id;
    private Long user3Id;

    @BeforeEach
    void setUp() throws Exception {
        user1Id = createTestUser("user1@example.com", "user1");
        user2Id = createTestUser("user2@example.com", "user2");
        user3Id = createTestUser("user3@example.com", "user3");
    }

    @Nested
    @DisplayName("팔로우")
    class Follow {

        @Test
        @DisplayName("다른 유저를 팔로우할 수 있다")
        void followSuccess() throws Exception {
            FollowUserRequest request = new FollowUserRequest(user1Id, user2Id);

            mockMvc.perform(post("/api/v1/user/relation/follow")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("이미 팔로우한 유저를 다시 팔로우하면 실패한다")
        void followAlreadyFollowing() throws Exception {
            FollowUserRequest request = new FollowUserRequest(user1Id, user2Id);

            // 첫 번째 팔로우
            mockMvc.perform(post("/api/v1/user/relation/follow")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());

            // 두 번째 팔로우 시도
            mockMvc.perform(post("/api/v1/user/relation/follow")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("언팔로우")
    class Unfollow {

        @Test
        @DisplayName("팔로우한 유저를 언팔로우할 수 있다")
        void unfollowSuccess() throws Exception {
            // given: 팔로우
            FollowUserRequest followRequest = new FollowUserRequest(user1Id, user2Id);
            mockMvc.perform(post("/api/v1/user/relation/follow")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(followRequest)))
                    .andExpect(status().isOk());

            // when & then: 언팔로우
            FollowUserRequest unfollowRequest = new FollowUserRequest(user1Id, user2Id);
            mockMvc.perform(post("/api/v1/user/relation/unfollow")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(unfollowRequest)))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("팔로우하지 않은 유저를 언팔로우하면 실패한다")
        void unfollowNotFollowing() throws Exception {
            FollowUserRequest request = new FollowUserRequest(user1Id, user2Id);

            mockMvc.perform(post("/api/v1/user/relation/unfollow")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("팔로워 목록 조회")
    class GetFollowers {

        @Test
        @DisplayName("팔로워 목록을 조회할 수 있다")
        void getFollowersSuccess() throws Exception {
            // given: user2, user3이 user1을 팔로우
            mockMvc.perform(post("/api/v1/user/relation/follow")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(new FollowUserRequest(user2Id, user1Id))))
                    .andExpect(status().isOk());

            mockMvc.perform(post("/api/v1/user/relation/follow")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(new FollowUserRequest(user3Id, user1Id))))
                    .andExpect(status().isOk());

            // when & then: user1의 팔로워 목록 조회
            mockMvc.perform(get("/api/v1/user/relation/{userId}/followers", user1Id))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content.length()").value(2))
                    .andExpect(jsonPath("$.hasNext").value(false));
        }

        @Test
        @DisplayName("팔로워가 없으면 빈 목록을 반환한다")
        void getFollowersEmpty() throws Exception {
            mockMvc.perform(get("/api/v1/user/relation/{userId}/followers", user1Id))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content.length()").value(0))
                    .andExpect(jsonPath("$.hasNext").value(false));
        }
    }

    @Nested
    @DisplayName("팔로잉 목록 조회")
    class GetFollowings {

        @Test
        @DisplayName("팔로잉 목록을 조회할 수 있다")
        void getFollowingsSuccess() throws Exception {
            // given: user1이 user2, user3을 팔로우
            mockMvc.perform(post("/api/v1/user/relation/follow")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(new FollowUserRequest(user1Id, user2Id))))
                    .andExpect(status().isOk());

            mockMvc.perform(post("/api/v1/user/relation/follow")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(new FollowUserRequest(user1Id, user3Id))))
                    .andExpect(status().isOk());

            // when & then: user1의 팔로잉 목록 조회
            mockMvc.perform(get("/api/v1/user/relation/{userId}/followings", user1Id))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray())
                    .andExpect(jsonPath("$.content.length()").value(2))
                    .andExpect(jsonPath("$.hasNext").value(false));
        }
    }

    @Nested
    @DisplayName("팔로우/언팔로우 시나리오")
    class FollowScenario {

        @Test
        @DisplayName("팔로우 후 언팔로우하면 팔로워 목록에서 제거된다")
        void followAndUnfollow() throws Exception {
            // given: user2가 user1을 팔로우
            mockMvc.perform(post("/api/v1/user/relation/follow")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(new FollowUserRequest(user2Id, user1Id))))
                    .andExpect(status().isOk());

            // user1의 팔로워 확인
            mockMvc.perform(get("/api/v1/user/relation/{userId}/followers", user1Id))
                    .andExpect(jsonPath("$.content.length()").value(1));

            // when: user2가 user1을 언팔로우
            mockMvc.perform(post("/api/v1/user/relation/unfollow")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(new FollowUserRequest(user2Id, user1Id))))
                    .andExpect(status().isOk());

            // then: user1의 팔로워 목록에서 제거됨
            mockMvc.perform(get("/api/v1/user/relation/{userId}/followers", user1Id))
                    .andDo(print())
                    .andExpect(jsonPath("$.content.length()").value(0));
        }
    }

    private Long createTestUser(String email, String nickname) throws Exception {
        CreateUserRequest request = new CreateUserRequest(
                Provider.LOCAL,
                email,
                "password123",
                "Test User",
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