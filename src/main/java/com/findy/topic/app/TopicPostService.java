package com.findy.topic.app;

import com.findy.post.domain.model.post.PostInfo;
import com.findy.topic.app.exception.TopicPostNotFoundException;
import com.findy.topic.app.exception.UnauthorizedTopicPostAccessException;
import com.findy.topic.app.interfaces.TopicMembershipRepository;
import com.findy.topic.app.interfaces.TopicPostRepository;
import com.findy.topic.app.interfaces.TopicRepository;
import com.findy.topic.domain.exception.OnlyTopicMemberCanWriteException;
import com.findy.topic.domain.model.post.TopicPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicPostService {
    private final TopicPostRepository topicPostRepository;
    private final TopicRepository topicRepository;
    private final TopicMembershipRepository membershipRepository;

    @Transactional
    public TopicPost createPost(Long topicId, Long authorId, String title, String content) {
        // Topic 존재 확인
        topicRepository.findById(topicId);

        // 작성자가 토픽 멤버인지 확인
        if (!membershipRepository.existsByUserIdAndTopicId(authorId, topicId)) {
            throw new OnlyTopicMemberCanWriteException();
        }

        PostInfo postInfo = new PostInfo(title, content);
        TopicPost post = new TopicPost(null, topicId, authorId, postInfo);

        return topicPostRepository.save(post);
    }

    @Transactional(readOnly = true)
    public TopicPost getPost(Long postId) {
        return topicPostRepository.findById(postId)
                .orElseThrow(() -> new TopicPostNotFoundException(postId));
    }

    @Transactional(readOnly = true)
    public List<TopicPost> getPostsByTopicId(Long topicId, Long cursor, int size) {
        // Topic 존재 확인
        topicRepository.findById(topicId);
        return topicPostRepository.findByTopicId(topicId, cursor, size);
    }

    @Transactional(readOnly = true)
    public List<TopicPost> getPostsByAuthorId(Long authorId, Long cursor, int size) {
        return topicPostRepository.findByAuthorId(authorId, cursor, size);
    }

    @Transactional
    public TopicPost updatePost(Long postId, Long userId, String title, String content) {
        TopicPost post = topicPostRepository.findById(postId)
                .orElseThrow(() -> new TopicPostNotFoundException(postId));

        // 작성자 확인
        if (!post.isAuthor(userId)) {
            throw new UnauthorizedTopicPostAccessException();
        }

        if (title != null) {
            post.updateTitle(title);
        }
        if (content != null) {
            post.updateContent(content);
        }

        return topicPostRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId, Long userId) {
        TopicPost post = topicPostRepository.findById(postId)
                .orElseThrow(() -> new TopicPostNotFoundException(postId));

        // 작성자 확인
        if (!post.isAuthor(userId)) {
            throw new UnauthorizedTopicPostAccessException();
        }

        topicPostRepository.deleteById(postId);
    }
}
