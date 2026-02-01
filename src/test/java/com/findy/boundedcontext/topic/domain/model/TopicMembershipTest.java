package com.findy.boundedcontext.topic.domain.model;

import com.findy.boundedcontext.topic.domain.exception.CannotAssignCreatorRoleException;
import com.findy.boundedcontext.topic.domain.exception.CreatorRoleCannotBeChangedException;
import com.findy.boundedcontext.topic.domain.model.membership.MemberRole;
import com.findy.boundedcontext.topic.domain.model.membership.TopicMembership;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TopicMembershipTest {

    @Test
    @DisplayName("Creator membership should be created correctly")
    void createCreatorMembership() {
        // given
        Long userId = 1L;
        Long topicId = 1L;

        // when
        TopicMembership membership = TopicMembership.createCreatorMembership(userId, topicId);

        // then
        assertThat(membership.getUserId()).isEqualTo(userId);
        assertThat(membership.getTopicId()).isEqualTo(topicId);
        assertThat(membership.getRole()).isEqualTo(MemberRole.CREATOR);
        assertThat(membership.isCreator()).isTrue();
        assertThat(membership.canManageTopic()).isTrue();
        assertThat(membership.canManageMembers()).isTrue();
    }

    @Test
    @DisplayName("Member membership should be created correctly")
    void createMemberMembership() {
        // given
        Long userId = 2L;
        Long topicId = 1L;

        // when
        TopicMembership membership = TopicMembership.createMemberMembership(userId, topicId);

        // then
        assertThat(membership.getUserId()).isEqualTo(userId);
        assertThat(membership.getTopicId()).isEqualTo(topicId);
        assertThat(membership.getRole()).isEqualTo(MemberRole.MEMBER);
        assertThat(membership.isCreator()).isFalse();
        assertThat(membership.canManageTopic()).isFalse();
        assertThat(membership.canManageMembers()).isFalse();
    }

    @Test
    @DisplayName("Member role should be changed to manager")
    void changeRoleToManager() {
        // given
        TopicMembership membership = TopicMembership.createMemberMembership(1L, 1L);

        // when
        TopicMembership updated = membership.changeRole(MemberRole.MANAGER);

        // then
        assertThat(updated.getRole()).isEqualTo(MemberRole.MANAGER);
        assertThat(updated.isManager()).isTrue();
        assertThat(updated.canManageMembers()).isTrue();
        assertThat(updated.canManageTopic()).isFalse();
    }

    @Test
    @DisplayName("Manager role should be changed to member")
    void changeRoleToMember() {
        // given
        TopicMembership membership = TopicMembership.createMemberMembership(1L, 1L);
        TopicMembership manager = membership.changeRole(MemberRole.MANAGER);

        // when
        TopicMembership updated = manager.changeRole(MemberRole.MEMBER);

        // then
        assertThat(updated.getRole()).isEqualTo(MemberRole.MEMBER);
        assertThat(updated.isManager()).isFalse();
        assertThat(updated.canManageMembers()).isFalse();
    }

    @Test
    @DisplayName("Creator role should not be changed")
    void creatorRoleCannotBeChanged() {
        // given
        TopicMembership creator = TopicMembership.createCreatorMembership(1L, 1L);

        // when & then
        assertThatThrownBy(() -> creator.changeRole(MemberRole.MANAGER))
                .isInstanceOf(CreatorRoleCannotBeChangedException.class)
                .hasMessage("Creator role cannot be changed");
    }

    @Test
    @DisplayName("Cannot change role to creator")
    void cannotChangeToCreatorRole() {
        // given
        TopicMembership membership = TopicMembership.createMemberMembership(1L, 1L);

        // when & then
        assertThatThrownBy(() -> membership.changeRole(MemberRole.CREATOR))
                .isInstanceOf(CannotAssignCreatorRoleException.class)
                .hasMessage("Cannot assign CREATOR role to member");
    }
}