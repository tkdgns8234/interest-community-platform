package com.findy.boundedcontext.topic.out.repository.entity;

import com.findy.global.entity.BaseTimeEntity;
import com.findy.boundedcontext.topic.domain.model.membership.MemberRole;
import com.findy.boundedcontext.topic.domain.model.membership.TopicMembership;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(
    name = "topic_membership",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_topic_membership_user_id_topic_id",
            columnNames = {"user_id", "topic_id"}
        )
    }
)
public class TopicMembershipEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long topicId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MemberRole role;

    public TopicMembershipEntity(TopicMembership membership) {
        this.id = membership.getId();
        this.userId = membership.getUserId();
        this.topicId = membership.getTopicId();
        this.role = membership.getRole();
    }

    public TopicMembership toTopicMembership() {
        return TopicMembership.builder()
                .id(this.id)
                .userId(this.userId)
                .topicId(this.topicId)
                .role(this.role)
                .build();
    }

    public void updateRole(MemberRole newRole) {
        this.role = newRole;
    }
}