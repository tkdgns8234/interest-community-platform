package com.findy.boundedcontext.topic.app.usecase;

import com.findy.boundedcontext.topic.app.dto.UpdateTopicCommand;
import com.findy.boundedcontext.topic.app.exception.UnauthorizedTopicAccessException;
import com.findy.boundedcontext.topic.app.interfaces.TopicRepository;
import com.findy.boundedcontext.topic.domain.model.topic.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateTopicUseCase {
    private final TopicRepository topicRepository;

    @Transactional
    public Topic execute(UpdateTopicCommand command) {
        Topic topic = topicRepository.findById(command.topicId());

        // 권한 확인 - 생성자만 수정 가능
        if (!topic.isCreator(command.userId())) {
            throw new UnauthorizedTopicAccessException();
        }

        topic.updateInfo(command.name(), command.introduction(), command.coverImageUrl());
        return topicRepository.save(topic);
    }
}
