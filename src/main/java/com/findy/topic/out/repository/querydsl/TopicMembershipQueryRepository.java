package com.findy.topic.out.repository.querydsl;

import com.findy.topic.out.repository.entity.QTopicMembershipEntity;
import com.findy.topic.out.repository.entity.TopicMembershipEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TopicMembershipQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<TopicMembershipEntity> findByTopicId(Long topicId, Long cursor, int size) {
        QTopicMembershipEntity membership = QTopicMembershipEntity.topicMembershipEntity;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(membership.topicId.eq(topicId));

        if (cursor != null) {
            builder.and(membership.id.lt(cursor));
        }

        return queryFactory
                .selectFrom(membership)
                .where(builder)
                .orderBy(membership.id.desc())
                .limit(size + 1)
                .fetch();
    }

    public List<TopicMembershipEntity> findByUserId(Long userId, Long cursor, int size) {
        QTopicMembershipEntity membership = QTopicMembershipEntity.topicMembershipEntity;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(membership.userId.eq(userId));

        if (cursor != null) {
            builder.and(membership.id.lt(cursor));
        }

        return queryFactory
                .selectFrom(membership)
                .where(builder)
                .orderBy(membership.id.desc())
                .limit(size + 1)
                .fetch();
    }
}