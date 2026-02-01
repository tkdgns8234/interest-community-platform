package com.findy.boundedcontext.topic.domain.model.membership;

import com.findy.boundedcontext.topic.domain.exception.CannotAssignCreatorRoleException;
import com.findy.boundedcontext.topic.domain.exception.CreatorRoleCannotBeChangedException;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class TopicMembership {
    private final Long id;
    private final Long userId;
    private final Long topicId;
    private final MemberRole role;

    public TopicMembership(Long userId, Long topicId, MemberRole role) {
        this(null, userId, topicId, role);
    }

    @Builder
    public TopicMembership(Long id, Long userId, Long topicId, MemberRole role) {
        this.id = id;
        this.userId = userId;
        this.topicId = topicId;
        this.role = role;
    }

    public static TopicMembership createCreatorMembership(Long userId, Long topicId) {
        return new TopicMembership(userId, topicId, MemberRole.CREATOR);
    }

    public static TopicMembership createMemberMembership(Long userId, Long topicId) {
        return new TopicMembership(userId, topicId, MemberRole.MEMBER);
    }

    public boolean isCreator() {
        return role == MemberRole.CREATOR;
    }

    public boolean isManager() {
        return role == MemberRole.MANAGER;
    }

    public boolean canManageTopic() {
        return role.canManageTopic();
    }

    public boolean canManageMembers() {
        return role.canManageMembers();
    }

    public TopicMembership changeRole(MemberRole newRole) {
        if (role == MemberRole.CREATOR) {
            throw new CreatorRoleCannotBeChangedException();
        }
        if (newRole == MemberRole.CREATOR) {
            throw new CannotAssignCreatorRoleException();
        }
        return TopicMembership.builder()
                .id(this.id)
                .userId(this.userId)
                .topicId(this.topicId)
                .role(newRole)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TopicMembership that = (TopicMembership) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}