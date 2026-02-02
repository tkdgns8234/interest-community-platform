package com.findy.boundedcontext.topic.app.usecase;

import com.findy.boundedcontext.topic.app.exception.TopicPostNotFoundException;
import com.findy.boundedcontext.topic.app.exception.UnauthorizedTopicPostAccessException;
import com.findy.boundedcontext.topic.app.interfaces.TopicPostRepository;
import com.findy.boundedcontext.topic.domain.model.post.TopicPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateTopicPostUseCase {
    private final TopicPostRepository topicPostRepository;

    @Transactional
    public TopicPost execute(Long postId, Long userId, String title, String content) {
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
}
