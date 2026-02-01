package com.findy.topic.app;

import com.findy.topic.app.exception.MembershipNotFoundException;
import com.findy.topic.app.exception.UnauthorizedTopicAccessException;
import com.findy.topic.app.interfaces.TopicMembershipRepository;
import com.findy.topic.app.interfaces.TopicRepository;
import com.findy.topic.domain.exception.CreatorCannotLeaveTopicException;
import com.findy.topic.domain.exception.DuplicateMembershipException;
import com.findy.topic.domain.model.membership.MemberRole;
import com.findy.topic.domain.model.topic.Topic;
import com.findy.topic.domain.model.membership.TopicMembership;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicMembershipService {
    private final TopicMembershipRepository membershipRepository;
    private final TopicRepository topicRepository;

    @Transactional
    public TopicMembership joinTopic(Long userId, Long topicId) {
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

    @Transactional
    public void leaveTopic(Long userId, Long topicId) {
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

    @Transactional(readOnly = true)
    public TopicMembership getMembership(Long userId, Long topicId) {
        return membershipRepository.findByUserIdAndTopicId(userId, topicId)
                .orElseThrow(() -> new MembershipNotFoundException("Membership not found"));
    }

    @Transactional(readOnly = true)
    public List<TopicMembership> getTopicMembers(Long topicId, Long cursor, int size) {
        // Topic 존재 확인
        topicRepository.findById(topicId);
        return membershipRepository.findByTopicId(topicId, cursor, size);
    }

    @Transactional(readOnly = true)
    public List<TopicMembership> getUserTopics(Long userId, Long cursor, int size) {
        return membershipRepository.findByUserId(userId, cursor, size);
    }

    @Transactional
    public TopicMembership updateMemberRole(Long requesterId, Long userId, Long topicId, MemberRole newRole) {
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