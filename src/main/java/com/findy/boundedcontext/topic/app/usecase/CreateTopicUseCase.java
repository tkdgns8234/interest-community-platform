package com.findy.boundedcontext.topic.app.usecase;

import com.findy.boundedcontext.topic.app.dto.CreateTopicCommand;
import com.findy.boundedcontext.topic.app.interfaces.TopicMembershipRepository;
import com.findy.boundedcontext.topic.app.interfaces.TopicRepository;
import com.findy.boundedcontext.topic.domain.model.membership.TopicMembership;
import com.findy.boundedcontext.topic.domain.model.topic.Topic;
import com.findy.boundedcontext.topic.domain.model.topic.TopicInfo;
import com.findy.boundedcontext.topic.out.interfaces.CategoryEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateTopicUseCase {
    private final TopicRepository topicRepository;
    private final TopicMembershipRepository membershipRepository;
    private final CategoryEntryPoint categoryEntryPoint;

    @Transactional
    public Topic execute(CreateTopicCommand command) {
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
}
