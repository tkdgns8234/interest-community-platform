package com.findy.boundedcontext.topic.app.usecase;

import com.findy.boundedcontext.topic.app.exception.MembershipNotFoundException;
import com.findy.boundedcontext.topic.app.interfaces.TopicMembershipRepository;
import com.findy.boundedcontext.topic.domain.model.membership.TopicMembership;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetMembershipUseCase {
    private final TopicMembershipRepository membershipRepository;

    @Transactional(readOnly = true)
    public TopicMembership execute(Long userId, Long topicId) {
        return membershipRepository.findByUserIdAndTopicId(userId, topicId)
                .orElseThrow(() -> new MembershipNotFoundException("Membership not found"));
    }
}
