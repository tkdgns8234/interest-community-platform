package com.findy.post.out.repository.querydsl;

import com.findy.post.out.repository.entity.PostEntity;
import com.findy.post.out.repository.entity.QPostEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<PostEntity> findAll(Long cursor, int size) {
        QPostEntity post = QPostEntity.postEntity;

        BooleanBuilder builder = new BooleanBuilder();
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
