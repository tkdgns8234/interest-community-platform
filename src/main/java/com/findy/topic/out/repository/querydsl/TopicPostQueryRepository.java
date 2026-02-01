package com.findy.topic.out.repository.querydsl;

import com.findy.topic.out.repository.entity.QTopicPostEntity;
import com.findy.topic.out.repository.entity.TopicPostEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TopicPostQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<TopicPostEntity> findByTopicId(Long topicId, Long cursor, int size) {
        QTopicPostEntity post = QTopicPostEntity.topicPostEntity;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(post.topicId.eq(topicId));

        if (cursor != null) {
            builder.and(post.id.lt(cursor));
        }

        return queryFactory
                .selectFrom(post)
                .where(builder)
                .orderBy(post.id.desc())
                .limit(size + 1)
                .fetch();
    }

    public List<TopicPostEntity> findByAuthorId(Long authorId, Long cursor, int size) {
        QTopicPostEntity post = QTopicPostEntity.topicPostEntity;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(post.authorId.eq(authorId));

        if (cursor != null) {
            builder.and(post.id.lt(cursor));
        }

        return queryFactory
                .selectFrom(post)
                .where(builder)
                .orderBy(post.id.desc())
                .limit(size + 1)
                .fetch();
    }
}
