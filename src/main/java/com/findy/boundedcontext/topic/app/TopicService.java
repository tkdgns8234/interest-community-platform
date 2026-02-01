package com.findy.boundedcontext.topic.app;

import com.findy.global.event.EventPublisher;
import com.findy.boundedcontext.topic.app.dto.CreateTopicCommand;
import com.findy.boundedcontext.topic.app.dto.UpdateTopicCommand;
import com.findy.boundedcontext.topic.app.exception.UnauthorizedTopicAccessException;
import com.findy.boundedcontext.topic.app.interfaces.TopicMembershipRepository;
import com.findy.boundedcontext.topic.app.interfaces.TopicRepository;
import com.findy.shared.topic.event.TopicDeletedEvent;
import com.findy.boundedcontext.topic.domain.model.membership.TopicMembership;
import com.findy.boundedcontext.topic.domain.model.topic.Topic;
import com.findy.boundedcontext.topic.domain.model.topic.TopicInfo;
import com.findy.boundedcontext.topic.out.interfaces.CategoryEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;
    private final TopicMembershipRepository membershipRepository;
    private final CategoryEntryPoint categoryEntryPoint;
    private final EventPublisher eventPublisher;

    @Transactional
    public Topic createTopic(CreateTopicCommand command) {
        // 카테고리 존재 확인
        categoryEntryPoint.validateCategoryExists(command.categoryId());

        TopicInfo topicInfo = new TopicInfo(
                command.name(),
                command.introduction(),
                command.coverImageUrl()
        );

        Topic topic = new Topic(
                null,
                command.categoryId(),
                command.creatorId(),
                topicInfo
        );

        topic = topicRepository.save(topic);

        // Creator Membership 자동 생성
        TopicMembership creatorMembership = TopicMembership.createCreatorMembership(
                command.creatorId(),
                topic.getId()
        );
        membershipRepository.save(creatorMembership);

        return topic;
    }

    @Transactional(readOnly = true)
    public Topic getTopic(Long id) {
        return topicRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Topic> getAllTopics(Long cursor, int size) {
        return topicRepository.findAll(cursor, size);
    }

    @Transactional(readOnly = true)
    public List<Topic> getTopicsByCategoryId(Long categoryId, Long cursor, int size) {
        // 카테고리 존재 확인
        categoryEntryPoint.validateCategoryExists(categoryId);
        return topicRepository.findByCategoryId(categoryId, cursor, size);
    }

    @Transactional
    public Topic updateTopic(UpdateTopicCommand command) {
        Topic topic = topicRepository.findById(command.topicId());

        // 권한 확인 - 생성자만 수정 가능
        if (!topic.isCreator(command.userId())) {
            throw new UnauthorizedTopicAccessException();
        }

        topic.updateInfo(command.name(), command.introduction(), command.coverImageUrl());
        return topicRepository.save(topic);
    }

    @Transactional
    public void deleteTopic(Long topicId, Long userId) {
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
