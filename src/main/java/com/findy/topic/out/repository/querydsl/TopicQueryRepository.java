package com.findy.topic.out.repository.querydsl;

import com.findy.topic.out.repository.entity.QTopicEntity;
import com.findy.topic.out.repository.entity.TopicEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TopicQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<TopicEntity> findAll(Long cursor, int size) {
        QTopicEntity topic = QTopicEntity.topicEntity;
        BooleanBuilder builder = new BooleanBuilder();

        if (cursor != null) {
            builder.and(topic.id.lt(cursor));
        }

        return queryFactory
                .selectFrom(topic)
                .where(builder)
                .orderBy(topic.id.desc())
                .limit(size + 1)
                .fetch();
    }

    public List<TopicEntity> findByCategoryId(Long categoryId, Long cursor, int size) {
        QTopicEntity topic = QTopicEntity.topicEntity;
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(topic.categoryId.eq(categoryId));

        if (cursor != null) {
            builder.and(topic.id.lt(cursor));
        }

        return queryFactory
                .selectFrom(topic)
                .where(builder)
                .orderBy(topic.id.desc())
                .limit(size + 1)
                .fetch();
    }
}
