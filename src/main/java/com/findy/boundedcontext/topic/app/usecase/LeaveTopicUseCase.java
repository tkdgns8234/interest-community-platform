package com.findy.boundedcontext.topic.app.usecase;

import com.findy.boundedcontext.topic.app.exception.MembershipNotFoundException;
import com.findy.boundedcontext.topic.app.interfaces.TopicMembershipRepository;
import com.findy.boundedcontext.topic.app.interfaces.TopicRepository;
import com.findy.boundedcontext.topic.domain.exception.CreatorCannotLeaveTopicException;
import com.findy.boundedcontext.topic.domain.model.membership.TopicMembership;
import com.findy.boundedcontext.topic.domain.model.topic.Topic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LeaveTopicUseCase {
    private final TopicMembershipRepository membershipRepository;
    private final TopicRepository topicRepository;

    @Transactional
    public void execute(Long userId, Long topicId) {
        // 멤버십 확인
        TopicMembership membership = membershipRepository.findByUserIdAndTopicId(userId, topicId)
                .orElseThrow(() -> new MembershipNotFoundException("Membership not found"));

        // Creator는 탈퇴 불가
        if (membership.isCreator()) {
            throw new CreatorCannotLeaveTopicException();
        }

        // 멤버십 삭제
        membershipRepository.deleteByUserIdAndTopicId(userId, topicId);

        // Topic의 memberCount 감소
        Topic topic = topicRepository.findById(topicId);
        topic.getMembershipManager().removeMember();
        topicRepository.save(topic);
    }
}
