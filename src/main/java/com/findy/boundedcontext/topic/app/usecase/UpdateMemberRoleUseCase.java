package com.findy.boundedcontext.topic.app.usecase;

import com.findy.boundedcontext.topic.app.exception.MembershipNotFoundException;
import com.findy.boundedcontext.topic.app.exception.UnauthorizedTopicAccessException;
import com.findy.boundedcontext.topic.app.interfaces.TopicMembershipRepository;
import com.findy.boundedcontext.topic.domain.model.membership.MemberRole;
import com.findy.boundedcontext.topic.domain.model.membership.TopicMembership;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateMemberRoleUseCase {
    private final TopicMembershipRepository membershipRepository;

    @Transactional
    public TopicMembership execute(Long requesterId, Long userId, Long topicId, MemberRole newRole) {
        // 요청자의 권한 확인
        TopicMembership requesterMembership = membershipRepository.findByUserIdAndTopicId(requesterId, topicId)
                .orElseThrow(() -> new MembershipNotFoundException("Requester membership not found"));

        if (!requesterMembership.canManageTopic()) {
            throw new UnauthorizedTopicAccessException();
        }

        // 대상 멤버십 조회
        TopicMembership targetMembership = membershipRepository.findByUserIdAndTopicId(userId, topicId)
                .orElseThrow(() -> new MembershipNotFoundException("Target membership not found"));

        // 역할 변경
        TopicMembership updated = targetMembership.changeRole(newRole);
        return membershipRepository.save(updated);
    }
}
