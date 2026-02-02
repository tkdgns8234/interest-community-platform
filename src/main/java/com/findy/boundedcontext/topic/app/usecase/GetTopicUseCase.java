package com.findy.boundedcontext.topic.app.usecase;

import com.findy.boundedcontext.topic.app.interfaces.TopicRepository;
import com.findy.boundedcontext.topic.domain.model.topic.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetTopicUseCase {
    private final TopicRepository topicRepository;

    @Transactional(readOnly = true)
    public Topic execute(Long id) {
        return topicRepository.findById(id);
    }
}
