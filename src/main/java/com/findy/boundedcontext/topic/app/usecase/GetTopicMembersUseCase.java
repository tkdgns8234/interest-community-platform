package com.findy.boundedcontext.topic.app.usecase;

import com.findy.boundedcontext.topic.app.interfaces.TopicMembershipRepository;
import com.findy.boundedcontext.topic.app.interfaces.TopicRepository;
import com.findy.boundedcontext.topic.domain.model.membership.TopicMembership;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTopicMembersUseCase {
    private final TopicMembershipRepository membershipRepository;
    private final TopicRepository topicRepository;

    @Transactional(readOnly = true)
    public List<TopicMembership> execute(Long topicId, Long cursor, int size) {
        // Topic 존재 확인
        topicRepository.findById(topicId);
        return membershipRepository.findByTopicId(topicId, cursor, size);
    }
}
