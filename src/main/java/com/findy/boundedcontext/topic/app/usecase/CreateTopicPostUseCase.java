package com.findy.boundedcontext.topic.app.usecase;

import com.findy.boundedcontext.topic.app.interfaces.TopicMembershipRepository;
import com.findy.boundedcontext.topic.app.interfaces.TopicPostRepository;
import com.findy.boundedcontext.topic.app.interfaces.TopicRepository;
import com.findy.boundedcontext.topic.domain.exception.OnlyTopicMemberCanWriteException;
import com.findy.boundedcontext.topic.domain.model.post.TopicPost;
import com.findy.shared.post.domain.PostInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateTopicPostUseCase {
    private final TopicPostRepository topicPostRepository;
    private final TopicRepository topicRepository;
    private final TopicMembershipRepository membershipRepository;

    @Transactional
    public TopicPost execute(Long topicId, Long authorId, String title, String content) {
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
}
