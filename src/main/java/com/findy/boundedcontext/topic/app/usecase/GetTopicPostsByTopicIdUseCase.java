package com.findy.boundedcontext.topic.app.usecase;

import com.findy.boundedcontext.topic.app.interfaces.TopicPostRepository;
import com.findy.boundedcontext.topic.app.interfaces.TopicRepository;
import com.findy.boundedcontext.topic.domain.model.post.TopicPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTopicPostsByTopicIdUseCase {
    private final TopicPostRepository topicPostRepository;
    private final TopicRepository topicRepository;

    @Transactional(readOnly = true)
    public List<TopicPost> execute(Long topicId, Long cursor, int size) {
        // Topic 존재 확인
        topicRepository.findById(topicId);
        return topicPostRepository.findByTopicId(topicId, cursor, size);
    }
}
