package com.findy.post.acceptance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.findy.post.in.rest.request.CreateCommentRequest;
import com.findy.post.in.rest.request.CreatePostRequest;
import com.findy.post.in.rest.request.LikeRequest;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class LikeAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("게시글에 좋아요를 추가할 수 있다")
    void likePost() throws Exception {
        Long postId = createTestPost(1L, "테스트 제목", "테스트 내용");
        LikeRequest request = new LikeRequest(1L);

        mockMvc.perform(post("/api/v1/post/{postId}/like", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("게시글 좋아요를 취소할 수 있다")
    void unlikePost() throws Exception {
        Long postId = createTestPost(1L, "테스트 제목", "테스트 내용");
        LikeRequest request = new LikeRequest(1L);

        mockMvc.perform(post("/api/v1/post/{postId}/like", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/api/v1/post/{postId}/like", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("댓글에 좋아요를 추가할 수 있다")
    void likeComment() throws Exception {
        Long postId = createTestPost(1L, "테스트 제목", "테스트 내용");
        Long commentId = createTestComment(postId, 1L, "테스트 댓글");
        LikeRequest request = new LikeRequest(1L);

        mockMvc.perform(post("/api/v1/post/{postId}/comment/{commentId}/like", postId, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("댓글 좋아요를 취소할 수 있다")
    void unlikeComment() throws Exception {
        Long postId = createTestPost(1L, "테스트 제목", "테스트 내용");
        Long commentId = createTestComment(postId, 1L, "테스트 댓글");
        LikeRequest request = new LikeRequest(1L);

        mockMvc.perform(post("/api/v1/post/{postId}/comment/{commentId}/like", postId, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/api/v1/post/{postId}/comment/{commentId}/like", postId, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNoContent());
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
