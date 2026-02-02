package com.findy.boundedcontext.post.app.usecase;

import com.findy.boundedcontext.post.app.interfaces.PostRepository;
import com.findy.boundedcontext.post.domain.model.post.Post;
import com.findy.boundedcontext.post.domain.model.post.PostInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GetAllPostsUseCaseTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private GetAllPostsUseCase getAllPostsUseCase;

    private Post testPost;

    @BeforeEach
    void setUp() {
        PostInfo postInfo = new PostInfo("테스트 제목", "테스트 내용");
        testPost = new Post(1L, 1L, postInfo);
    }

    @Test
    @DisplayName("전체 게시글을 조회할 수 있다")
    void getAllPosts() {
        given(postRepository.findAll(null, 20)).willReturn(List.of(testPost));

        List<Post> posts = getAllPostsUseCase.execute(null, 20);

        assertThat(posts).hasSize(1);
        assertThat(posts.get(0).getId()).isEqualTo(1L);
    }
}
