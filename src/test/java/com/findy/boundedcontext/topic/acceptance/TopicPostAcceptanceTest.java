package com.findy.boundedcontext.topic.acceptance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.findy.boundedcontext.category.in.rest.request.CreateCategoryRequest;
import com.findy.boundedcontext.topic.in.rest.request.CreateTopicPostRequest;
import com.findy.boundedcontext.topic.in.rest.request.CreateTopicRequest;
import com.findy.boundedcontext.topic.in.rest.request.DeleteTopicPostRequest;
import com.findy.boundedcontext.topic.in.rest.request.JoinTopicRequest;
import com.findy.boundedcontext.topic.in.rest.request.UpdateTopicPostRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class TopicPostAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long categoryId;
    private Long topicId;
    private Long creatorId = 100L;
    private Long memberId = 200L;

    @BeforeEach
    void setUp() throws Exception {
        // Category 생성
        categoryId = createTestCategory("테스트 카테고리", "카테고리 설명");
        // Topic 생성 (creator가 자동으로 멤버가 됨)
        topicId = createTestTopic(creatorId, "테스트 토픽", "토픽 소개");
    }

    @Test
    @DisplayName("토픽 멤버는 게시글을 생성할 수 있다")
    void createPostByMember() throws Exception {
        CreateTopicPostRequest request = new CreateTopicPostRequest(creatorId, "테스트 제목", "테스트 내용");

        mockMvc.perform(post("/api/v1/topics/{topicId}/posts", topicId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Test
    @DisplayName("토픽 멤버가 아니면 게시글을 생성할 수 없다")
    void cannotCreatePostByNonMember() throws Exception {
        CreateTopicPostRequest request = new CreateTopicPostRequest(999L, "테스트 제목", "테스트 내용");

        mockMvc.perform(post("/api/v1/topics/{topicId}/posts", topicId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("토픽 게시글을 조회할 수 있다")
    void getPost() throws Exception {
        Long postId = createTestPost(topicId, creatorId, "테스트 제목", "테스트 내용");

        mockMvc.perform(get("/api/v1/topics/{topicId}/posts/{postId}", topicId, postId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.topicId").value(topicId))
                .andExpect(jsonPath("$.authorId").value(creatorId))
                .andExpect(jsonPath("$.title").value("테스트 제목"))
                .andExpect(jsonPath("$.content").value("테스트 내용"));
    }

    @Test
    @DisplayName("작성자는 게시글을 수정할 수 있다")
    void updatePostByAuthor() throws Exception {
        Long postId = createTestPost(topicId, creatorId, "테스트 제목", "테스트 내용");

        UpdateTopicPostRequest request = new UpdateTopicPostRequest(creatorId, "수정된 제목", "수정된 내용");

        mockMvc.perform(patch("/api/v1/topics/{topicId}/posts/{postId}", topicId, postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("수정된 제목"))
                .andExpect(jsonPath("$.content").value("수정된 내용"));
    }

    @Test
    @DisplayName("작성자가 아니면 게시글을 수정할 수 없다")
    void cannotUpdatePostByNonAuthor() throws Exception {
        // 멤버 추가
        joinTopic(topicId, memberId);
        Long postId = createTestPost(topicId, creatorId, "테스트 제목", "테스트 내용");

        UpdateTopicPostRequest request = new UpdateTopicPostRequest(memberId, "수정된 제목", "수정된 내용");

        mockMvc.perform(patch("/api/v1/topics/{topicId}/posts/{postId}", topicId, postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("작성자는 게시글을 삭제할 수 있다")
    void deletePostByAuthor() throws Exception {
        Long postId = createTestPost(topicId, creatorId, "테스트 제목", "테스트 내용");

        DeleteTopicPostRequest request = new DeleteTopicPostRequest(creatorId);

        mockMvc.perform(delete("/api/v1/topics/{topicId}/posts/{postId}", topicId, postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("작성자가 아니면 게시글을 삭제할 수 없다")
    void cannotDeletePostByNonAuthor() throws Exception {
        // 멤버 추가
        joinTopic(topicId, memberId);
        Long postId = createTestPost(topicId, creatorId, "테스트 제목", "테스트 내용");

        DeleteTopicPostRequest request = new DeleteTopicPostRequest(memberId);

        mockMvc.perform(delete("/api/v1/topics/{topicId}/posts/{postId}", topicId, postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("토픽의 게시글 목록을 페이징 조회할 수 있다")
    void getPostsByTopic() throws Exception {
        createTestPost(topicId, creatorId, "제목1", "내용1");
        createTestPost(topicId, creatorId, "제목2", "내용2");
        createTestPost(topicId, creatorId, "제목3", "내용3");

        String firstPageResponse = mockMvc.perform(get("/api/v1/topics/{topicId}/posts", topicId)
                        .param("size", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.hasNext").value(true))
                .andExpect(jsonPath("$.nextCursor").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long nextCursor = objectMapper.readTree(firstPageResponse).get("nextCursor").asLong();

        mockMvc.perform(get("/api/v1/topics/{topicId}/posts", topicId)
                        .param("cursor", String.valueOf(nextCursor))
                        .param("size", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.hasNext").value(false))
                .andExpect(jsonPath("$.nextCursor").isEmpty());
    }

    @Test
    @DisplayName("사용자가 작성한 토픽 게시글 목록을 조회할 수 있다")
    void getUserTopicPosts() throws Exception {
        createTestPost(topicId, creatorId, "제목1", "내용1");
        createTestPost(topicId, creatorId, "제목2", "내용2");

        mockMvc.perform(get("/api/v1/users/{userId}/topic-posts", creatorId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    private Long createTestCategory(String name, String description) throws Exception {
        CreateCategoryRequest request = new CreateCategoryRequest(null, name, description, null);

        String response = mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(response).get("id").asLong();
    }

    private Long createTestTopic(Long creatorId, String name, String introduction) throws Exception {
        CreateTopicRequest request = new CreateTopicRequest(categoryId, creatorId, name, introduction, null);

        String response = mockMvc.perform(post("/api/v1/topics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(response).get("id").asLong();
    }

    private void joinTopic(Long topicId, Long userId) throws Exception {
        JoinTopicRequest request = new JoinTopicRequest(userId);

        mockMvc.perform(post("/api/v1/topics/{topicId}/memberships", topicId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    private Long createTestPost(Long topicId, Long authorId, String title, String content) throws Exception {
        CreateTopicPostRequest request = new CreateTopicPostRequest(authorId, title, content);

        String response = mockMvc.perform(post("/api/v1/topics/{topicId}/posts", topicId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(response).get("id").asLong();
    }
}
