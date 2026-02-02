package com.findy.boundedcontext.topic.app.usecase;

import com.findy.global.event.EventPublisher;
import com.findy.boundedcontext.topic.app.exception.UnauthorizedTopicAccessException;
import com.findy.boundedcontext.topic.app.interfaces.TopicRepository;
import com.findy.boundedcontext.topic.domain.model.topic.Topic;
import com.findy.shared.topic.event.TopicDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteTopicUseCase {
    private final TopicRepository topicRepository;
    private final EventPublisher eventPublisher;

    @Transactional
    public void execute(Long topicId, Long userId) {
        Topic topic = topicRepository.findById(topicId);

        // 권한 확인 - 생성자만 삭제 가능
        if (!topic.isCreator(userId)) {
            throw new UnauthorizedTopicAccessException();
        }

        topicRepository.deleteById(topicId);

        // TODO:: topic 삭제 시 하위 컨텐츠 삭제 처리
        TopicDeletedEvent event = new TopicDeletedEvent(
                topicId,
                userId
        );
        eventPublisher.publish(event);
    }
}
