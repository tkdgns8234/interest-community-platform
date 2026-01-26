package com.findy.user.out.repository.querydsl;

import com.findy.user.out.repository.entity.QUserEntity;
import com.findy.user.out.repository.entity.QUserRelationEntity;
import com.findy.user.out.repository.entity.UserEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRelationQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<UserEntity> findFollowers(Long userId, Long cursor, int size) {
        QUserRelationEntity relation = QUserRelationEntity.userRelationEntity;
        QUserEntity user = QUserEntity.userEntity;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(relation.targetUserId.eq(userId));

        if (cursor != null) {
            builder.and(relation.userId.lt(cursor));
        }

        return queryFactory
                .selectFrom(user)
                .where(user.id.in(
                        queryFactory
                                .select(relation.userId)
                                .from(relation)
                                .where(builder)
                ))
                .orderBy(user.id.desc())
                .limit(size + 1)
                .fetch();
    }

    public List<UserEntity> findFollowings(Long userId, Long cursor, int size) {
        QUserRelationEntity relation = QUserRelationEntity.userRelationEntity;
        QUserEntity user = QUserEntity.userEntity;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(relation.userId.eq(userId));

        if (cursor != null) {
            builder.and(relation.targetUserId.lt(cursor));
        }

        return queryFactory
                .selectFrom(user)
                .where(user.id.in(
                        queryFactory
                                .select(relation.targetUserId)
                                .from(relation)
                                .where(builder)
                ))
                .orderBy(user.id.desc())
                .limit(size + 1)
                .fetch();
    }
}