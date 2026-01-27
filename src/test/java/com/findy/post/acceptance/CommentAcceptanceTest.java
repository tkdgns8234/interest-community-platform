package com.findy.post.acceptance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.findy.post.in.rest.request.CreateCommentRequest;
import com.findy.post.in.rest.request.CreatePostRequest;
import com.findy.post.in.rest.request.UpdateCommentRequest;
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
class CommentAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("댓글을 생성할 수 있다")
    void createComment() throws Exception {
        Long postId = createTestPost(1L, "테스트 제목", "테스트 내용");
        CreateCommentRequest request = new CreateCommentRequest(1L, "테스트 댓글");

        mockMvc.perform(post("/api/v1/post/{postId}/comment", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Test
    @DisplayName("댓글을 조회할 수 있다")
    void getComment() throws Exception {
        Long postId = createTestPost(1L, "테스트 제목", "테스트 내용");
        Long commentId = createTestComment(postId, 1L, "테스트 댓글");

        mockMvc.perform(get("/api/v1/post/{postId}/comment/{commentId}", postId, commentId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(commentId))
                .andExpect(jsonPath("$.content").value("테스트 댓글"));
    }

    @Test
    @DisplayName("댓글을 수정할 수 있다")
    void updateComment() throws Exception {
        Long postId = createTestPost(1L, "테스트 제목", "테스트 내용");
        Long commentId = createTestComment(postId, 1L, "테스트 댓글");

        UpdateCommentRequest request = new UpdateCommentRequest("수정된 댓글");

        mockMvc.perform(patch("/api/v1/post/{postId}/comment/{commentId}", postId, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/post/{postId}/comment/{commentId}", postId, commentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value("수정된 댓글"));
    }

    @Test
    @DisplayName("댓글을 삭제할 수 있다")
    void deleteComment() throws Exception {
        Long postId = createTestPost(1L, "테스트 제목", "테스트 내용");
        Long commentId = createTestComment(postId, 1L, "테스트 댓글");

        mockMvc.perform(delete("/api/v1/post/{postId}/comment/{commentId}", postId, commentId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("게시글의 전체 댓글을 조회할 수 있다")
    void getCommentsByPostId() throws Exception {
        Long postId = createTestPost(1L, "테스트 제목", "테스트 내용");
        createTestComment(postId, 1L, "댓글1");
        createTestComment(postId, 1L, "댓글2");
        createTestComment(postId, 1L, "댓글3");

        mockMvc.perform(get("/api/v1/post/{postId}/comment", postId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3));
    }

    private Long createTestPost(Long authorId, String title, String content) throws Exception {
        CreatePostRequest request = new CreatePostRequest(authorId, title, content);

        String response = mockMvc.perform(post("/api/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(response).get("id").asLong();
    }

    private Long createTestComment(Long postId, Long authorId, String content) throws Exception {
        CreateCommentRequest request = new CreateCommentRequest(authorId, content);

        String response = mockMvc.perform(post("/api/v1/post/{postId}/comment", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(response).get("id").asLong();
    }
}
