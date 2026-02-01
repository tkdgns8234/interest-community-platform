package com.findy.topic.app;

import com.findy.common.domain.PostInfo;
import com.findy.topic.app.exception.TopicPostNotFoundException;
import com.findy.topic.app.exception.UnauthorizedTopicPostAccessException;
import com.findy.topic.app.interfaces.TopicMembershipRepository;
import com.findy.topic.app.interfaces.TopicPostRepository;
import com.findy.topic.app.interfaces.TopicRepository;
import com.findy.topic.domain.exception.OnlyTopicMemberCanWriteException;
import com.findy.topic.domain.model.post.TopicPost;
import com.findy.topic.domain.model.topic.Topic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TopicPostServiceTest {

    @Mock
    private TopicPostRepository topicPostRepository;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private TopicMembershipRepository membershipRepository;

    @InjectMocks
    private TopicPostService topicPostService;

    private TopicPost testPost;
    private Topic testTopic;

    @BeforeEach
    void setUp() {
        PostInfo postInfo = new PostInfo("테스트 제목", "테스트 내용");
        testPost = new TopicPost(1L, 10L, 100L, postInfo);
    }

    @Nested
    @DisplayName("토픽 게시글 생성")
    class CreateTopicPost {

        @Test
        @DisplayName("토픽 멤버는 게시글을 생성할 수 있다")
        void createPostByTopicMember() {
            given(topicRepository.findById(10L)).willReturn(testTopic);
            given(membershipRepository.existsByUserIdAndTopicId(100L, 10L)).willReturn(true);
            given(topicPostRepository.save(any(TopicPost.class))).willReturn(testPost);

            TopicPost createdPost = topicPostService.createPost(10L, 100L, "테스트 제목", "테스트 내용");

            assertThat(createdPost).isNotNull();
            assertThat(createdPost.getTopicId()).isEqualTo(10L);
            assertThat(createdPost.getAuthorId()).isEqualTo(100L);
            assertThat(createdPost.getTitle()).isEqualTo("테스트 제목");
            assertThat(createdPost.getContent()).isEqualTo("테스트 내용");
            verify(topicPostRepository).save(any(TopicPost.class));
        }

        @Test
        @DisplayName("토픽 멤버가 아니면 게시글을 생성할 수 없다")
        void cannotCreatePostByNonMember() {
            given(topicRepository.findById(10L)).willReturn(testTopic);
            given(membershipRepository.existsByUserIdAndTopicId(200L, 10L)).willReturn(false);

            assertThatThrownBy(() -> topicPostService.createPost(10L, 200L, "제목", "내용"))
                    .isInstanceOf(OnlyTopicMemberCanWriteException.class)
                    .hasMessage("Only topic members can write posts");
        }
    }

    @Nested
    @DisplayName("토픽 게시글 조회")
    class GetTopicPost {

        @Test
        @DisplayName("ID로 게시글을 조회할 수 있다")
        void getPost() {
            given(topicPostRepository.findById(1L)).willReturn(Optional.of(testPost));

            TopicPost post = topicPostService.getPost(1L);

            assertThat(post).isNotNull();
            assertThat(post.getId()).isEqualTo(1L);
            assertThat(post.getTitle()).isEqualTo("테스트 제목");
        }

        @Test
        @DisplayName("존재하지 않는 게시글 조회 시 예외가 발생한다")
        void getPostNotFound() {
            given(topicPostRepository.findById(999L)).willReturn(Optional.empty());

            assertThatThrownBy(() -> topicPostService.getPost(999L))
                    .isInstanceOf(TopicPostNotFoundException.class);
        }

        @Test
        @DisplayName("토픽별 게시글 목록을 조회할 수 있다")
        void getPostsByTopicId() {
            given(topicRepository.findById(10L)).willReturn(testTopic);
            given(topicPostRepository.findByTopicId(10L, null, 20)).willReturn(List.of(testPost));

            List<TopicPost> posts = topicPostService.getPostsByTopicId(10L, null, 20);

            assertThat(posts).hasSize(1);
            assertThat(posts.get(0).getTopicId()).isEqualTo(10L);
        }

        @Test
        @DisplayName("작성자별 게시글 목록을 조회할 수 있다")
        void getPostsByAuthorId() {
            given(topicPostRepository.findByAuthorId(100L, null, 20)).willReturn(List.of(testPost));

            List<TopicPost> posts = topicPostService.getPostsByAuthorId(100L, null, 20);

            assertThat(posts).hasSize(1);
            assertThat(posts.get(0).getAuthorId()).isEqualTo(100L);
        }
    }

    @Nested
    @DisplayName("토픽 게시글 수정")
    class UpdateTopicPost {

        @Test
        @DisplayName("작성자는 게시글을 수정할 수 있다")
        void updatePostByAuthor() {
            given(topicPostRepository.findById(1L)).willReturn(Optional.of(testPost));
            given(topicPostRepository.save(any(TopicPost.class))).willReturn(testPost);

            TopicPost updatedPost = topicPostService.updatePost(1L, 100L, "수정된 제목", "수정된 내용");

            assertThat(updatedPost).isNotNull();
            verify(topicPostRepository).save(any(TopicPost.class));
        }

        @Test
        @DisplayName("작성자가 아니면 게시글을 수정할 수 없다")
        void cannotUpdatePostByNonAuthor() {
            given(topicPostRepository.findById(1L)).willReturn(Optional.of(testPost));

            assertThatThrownBy(() -> topicPostService.updatePost(1L, 200L, "제목", "내용"))
                    .isInstanceOf(UnauthorizedTopicPostAccessException.class)
                    .hasMessage("Unauthorized access to topic post");
        }
    }

    @Nested
    @DisplayName("토픽 게시글 삭제")
    class DeleteTopicPost {

        @Test
        @DisplayName("작성자는 게시글을 삭제할 수 있다")
        void deletePostByAuthor() {
            given(topicPostRepository.findById(1L)).willReturn(Optional.of(testPost));

            topicPostService.deletePost(1L, 100L);

            verify(topicPostRepository).deleteById(1L);
        }

        @Test
        @DisplayName("작성자가 아니면 게시글을 삭제할 수 없다")
        void cannotDeletePostByNonAuthor() {
            given(topicPostRepository.findById(1L)).willReturn(Optional.of(testPost));

            assertThatThrownBy(() -> topicPostService.deletePost(1L, 200L))
                    .isInstanceOf(UnauthorizedTopicPostAccessException.class)
                    .hasMessage("Unauthorized access to topic post");
        }
    }
}
