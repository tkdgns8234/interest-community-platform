package com.findy.boundedcontext.topic.app.usecase;

import com.findy.boundedcontext.topic.app.interfaces.TopicMembershipRepository;
import com.findy.boundedcontext.topic.app.interfaces.TopicRepository;
import com.findy.boundedcontext.topic.domain.exception.DuplicateMembershipException;
import com.findy.boundedcontext.topic.domain.model.membership.TopicMembership;
import com.findy.boundedcontext.topic.domain.model.topic.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinTopicUseCase {
    private final TopicMembershipRepository membershipRepository;
    private final TopicRepository topicRepository;

    @Transactional
    public TopicMembership execute(Long userId, Long topicId) {
        // Topic 존재 확인
        Topic topic = topicRepository.findById(topicId);

        // 이미 가입했는지 확인
        if (membershipRepository.existsByUserIdAndTopicId(userId, topicId)) {
            throw new DuplicateMembershipException("User already joined this topic");
        }

        // 멤버십 생성
        TopicMembership membership = TopicMembership.createMemberMembership(userId, topicId);
        membership = membershipRepository.save(membership);

        // Topic의 memberCount 증가
        topic.getMembershipManager().addMember();
        topicRepository.save(topic);

        return membership;
    }
}
