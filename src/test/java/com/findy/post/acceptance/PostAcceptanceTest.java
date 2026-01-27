package com.findy.post.acceptance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.findy.post.in.rest.request.CreatePostRequest;
import com.findy.post.in.rest.request.UpdatePostRequest;
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
class PostAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("게시글을 생성할 수 있다")
    void createPost() throws Exception {
        CreatePostRequest request = new CreatePostRequest(1L, "테스트 제목", "테스트 내용");

        mockMvc.perform(post("/api/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber());
    }

    @Test
    @DisplayName("게시글을 조회할 수 있다")
    void getPost() throws Exception {
        Long postId = createTestPost(1L, "테스트 제목", "테스트 내용");

        mockMvc.perform(get("/api/v1/post/{postId}", postId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(postId))
                .andExpect(jsonPath("$.title").value("테스트 제목"))
                .andExpect(jsonPath("$.content").value("테스트 내용"));
    }

    @Test
    @DisplayName("게시글을 수정할 수 있다")
    void updatePost() throws Exception {
        Long postId = createTestPost(1L, "테스트 제목", "테스트 내용");

        UpdatePostRequest request = new UpdatePostRequest("수정된 제목", "수정된 내용");

        mockMvc.perform(patch("/api/v1/post/{postId}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/post/{postId}", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("수정된 제목"))
                .andExpect(jsonPath("$.content").value("수정된 내용"));
    }

    @Test
    @DisplayName("게시글을 삭제할 수 있다")
    void deletePost() throws Exception {
        Long postId = createTestPost(1L, "테스트 제목", "테스트 내용");

        mockMvc.perform(delete("/api/v1/post/{postId}", postId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("전체 게시글을 페이징 조회할 수 있다")
    void getAllPosts() throws Exception {
        createTestPost(1L, "제목1", "내용1");
        createTestPost(1L, "제목2", "내용2");
        createTestPost(1L, "제목3", "내용3");

        String firstPageResponse = mockMvc.perform(get("/api/v1/post")
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

        mockMvc.perform(get("/api/v1/post")
                        .param("cursor", String.valueOf(nextCursor))
                        .param("size", "2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.hasNext").value(false))
                .andExpect(jsonPath("$.nextCursor").isEmpty());
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
}
